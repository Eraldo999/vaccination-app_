package com.example.vaccination_app.service;

import com.example.vaccination_app.dto.BookingCreateDto;
import com.example.vaccination_app.exception.PermissionDeniedException;
import com.example.vaccination_app.exception.ResourceAlreadyExistsException;
import com.example.vaccination_app.exception.ResourceNotFoundException;
import com.example.vaccination_app.model.Approved;
import com.example.vaccination_app.model.Booking;
import com.example.vaccination_app.model.Notification;
import com.example.vaccination_app.model.User;
import com.example.vaccination_app.model.enums.AnswersStatus;
import com.example.vaccination_app.model.enums.Status;
import com.example.vaccination_app.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

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

    @Transactional
    public void dissaproveBooking (long id){
        var optbooking = bookingRepository.findById(id);
        if(optbooking.isEmpty()){
            throw new ResourceNotFoundException();
        }
        var booking = optbooking.get();
        if (booking.getStatus().equals(Status.PENDING)) {
            booking.setStatus(Status.DISSAPROVED);
            var user = booking.getUser();
            var notification = new Notification();
            notification.setStatus("Booking nr " + booking.getId() + " is denied!");
            notification.setUser(user);
            notificationRepository.save(notification);
        }
        else throw new PermissionDeniedException();
    }

    @Transactional
    public void acceptBooking (long id){
        var optbooking = bookingRepository.findById(id);
        if(optbooking.isEmpty()){
            throw new ResourceNotFoundException();
        }
        var status = Status.APPROVED;
        var booking = optbooking.get();
        if (booking.getStatus().equals(Status.PENDING)) {
            booking.setStatus(status);
            var vaccine = booking.getVaccine();
            vaccine.setQuantity(vaccine.getQuantity() - 1);
            var user = booking.getUser();
            var date = booking.getDate();
            var notification = new Notification();
            notification.setStatus("Your book is aproved!" + " First dose date: " + dateFormat.format(booking.getDate()));
            notification.setUser(user);
            var approved = new Approved();
            approved.setBooking(booking);
            approvedRepository.save(approved);
            notificationRepository.save(notification);
        }
    }
    @Transactional
    public boolean checkIfUserHasAnApprovedBooking (User user){
        var bookings = user.getBookings();
        boolean bol = false;
        for (Booking b : bookings){
            if(b.getStatus().equals(Status.APPROVED) || b.getStatus().equals(Status.DONE)){
                bol = false;
            }
            else {
                bol = false;
            }
        }
        return bol;
    }

    @Transactional
    public void createBooking (BookingCreateDto req, Principal principal, long vaccineId){
        var optUser = userRepository.findByEmail(principal.getName());
        var optVaccine = vaccineRepository.findById(vaccineId);
        if(optUser.isEmpty() || optVaccine.isEmpty()){
            throw new ResourceNotFoundException();
        }
        var status = Status.PENDING;
        var user = optUser.get();
        var bookings = user.getBookings();
        Set<Date> dates = new HashSet<>();
        for (Booking b : bookings){
            dates.add(b.getDate());
        }

        if (user.getAnswers().getStatus().equals(AnswersStatus.COMPLETED)
            && checkIfUserHasAnApprovedBooking(user) == false) {
            var vaccine = optVaccine.get();
            if (vaccine.getQuantity() == 0) {
                throw new ResourceNotFoundException("Vaccine out of stock");
            }
            var vaccinationCenter = user.getVaccinationCenter();

            var booking = new Booking();
            for (Date d : dates){
                if (req.getDate().equals(d)){
                    throw new ResourceAlreadyExistsException();

                }
            }
            booking.setDate(req.getDate());
            booking.setTime(req.getTime());
            booking.setVaccine(vaccine);
            booking.setVaccinationCenter(vaccinationCenter);
            booking.setUser(user);
            booking.setStatus(status);
            String dateconvert = milatryConvert(req.getDate() +"");

            var optAdmin = userRepository.findById(1L);
            var notification = new Notification();
            notification.setStatus("New Booking by " + user.getName());
            notification.setUser(optAdmin.get());

            notificationRepository.save(notification);
            bookingRepository.save(booking);
        }
        else{
            throw new PermissionDeniedException();
        }

    }


    private String milatryConvert (String date){
        int lastCharIndex = date.length() -1;
        int secondLastCharIndex = date.length() -2;
        String dateWithoutAMPM = date.substring(0, date.length() -2)
        if (date.substring(lastCharIndex, secondLastCharIndex) =="AM"){
            return dateWithoutAMPM;
        }
        else{
            return "";
        }
//        int convert = Integer.parseInt(dateWithoutAMPM) + 12;


    }

    public void deleteBookingById (long id){
        var optbooking = bookingRepository.findById(id);
        if (optbooking.isEmpty()){
            throw new ResourceNotFoundException();
        }
        var booking = optbooking.get();
        if (booking.getStatus().equals(Status.DISSAPROVED)){
            bookingRepository.deleteById(id);
        }
        else throw new PermissionDeniedException();
    }

    public Set<Booking> getBookingsForUser (Principal principal){
        var user = userRepository.findByEmail(principal.getName());
        if(user.isEmpty()) {
            throw new ResourceNotFoundException();
        }
        var usr = user.get();
        return usr.getBookings();
    }



}
