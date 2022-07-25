package com.example.vaccination_app.service;

import com.example.vaccination_app.dto.BookingCreateDto;
import com.example.vaccination_app.exception.ResourceNotFoundException;
import com.example.vaccination_app.model.Approved;
import com.example.vaccination_app.model.Booking;
import com.example.vaccination_app.model.Notification;
import com.example.vaccination_app.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final VaccineRepository vaccineRepository;
    private final VaccinationCenterRepository vaccinationCenterRepository;
    private final NotificationRepository notificationRepository;
    private final ApprovedRepository approvedRepository;
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

    @Autowired
    public BookingService(BookingRepository bookingRepository, UserRepository userRepository,
                          VaccineRepository vaccineRepository, VaccinationCenterRepository vaccinationCenterRepository,
                          NotificationRepository notificationRepository,ApprovedRepository approvedRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.vaccineRepository = vaccineRepository;
        this.vaccinationCenterRepository = vaccinationCenterRepository;
        this.notificationRepository = notificationRepository;
        this.approvedRepository = approvedRepository;
    }

    public List<Booking> getAllBookings (){
        return bookingRepository.findAll();
    }

    public void deleteBookingById (long id){
        var optbooking = bookingRepository.findById(id);
        if(optbooking.isEmpty()){
            throw new ResourceNotFoundException();
        }
        var booking = optbooking.get();
        var user = booking.getUser();
        var notification = new Notification();
        notification.setStatus("Booking nr " + booking.getId() + " is denied!");
        notification.setUser(user);
        notificationRepository.save(notification);
        bookingRepository.deleteById(id);
    }

    public void acceptBooking (long id){
        var optbooking = bookingRepository.findById(id);
        if(optbooking.isEmpty()){
            throw new ResourceNotFoundException();
        }
        var booking = optbooking.get();
        var user = booking.getUser();
        var date = booking.getDate();
        var notification = new Notification();
        notification.setStatus("Your book is aproved!" + " First dose date: " + dateFormat.format(booking.getDate()));
        notification.setUser(user);
        var approved = new Approved();
        approved.setBooking(booking);
        booking.setApprove(true);
        approvedRepository.save(approved);
        notificationRepository.save(notification);
    }

    @Transactional
    public void createBooking (BookingCreateDto req, Principal principal, long vaccinationCenterId, long vaccineId){
        var optUser = userRepository.findByEmail(principal.getName());
        var optVaccine = vaccineRepository.findById(vaccineId);
        var optVaccinationCenter = vaccinationCenterRepository.findById(vaccinationCenterId);
        if(optUser.isEmpty() || optVaccine.isEmpty() || optVaccinationCenter.isEmpty()){
            throw new ResourceNotFoundException();
        }
        var user = optUser.get();
        var vaccine = optVaccine.get();
        var vaccinationCenter = optVaccinationCenter.get();

        var booking = new Booking();
        booking.setDate(req.getDate());
        booking.setLocation(req.getLocation());
        booking.setVaccinationCenter(vaccinationCenter);
        booking.setVaccine(vaccine);
        booking.setUser(user);

        var optAdmin = userRepository.findById(1L);
        var notification = new Notification();
        notification.setStatus("New Booking by " + user.getName());
        notification.setUser(optAdmin.get());

        notificationRepository.save(notification);
        bookingRepository.save(booking);

    }



}
