package com.example.vaccination_app.service;

import com.example.vaccination_app.exception.ResourceNotFoundException;
import com.example.vaccination_app.model.Approved;
import com.example.vaccination_app.model.Booking;
import com.example.vaccination_app.model.Notification;
import com.example.vaccination_app.model.enums.Status;
import com.example.vaccination_app.repository.ApprovedRepository;
import com.example.vaccination_app.repository.BookingRepository;
import com.example.vaccination_app.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ApprovedService {

    private final ApprovedRepository approvedRepository;
    private final BookingRepository bookingRepository;
    private final NotificationRepository notificationRepository;
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

    @Autowired
    public ApprovedService(ApprovedRepository approvedRepository, BookingRepository bookingRepository,
                           NotificationRepository notificationRepository) {
        this.approvedRepository = approvedRepository;
        this.bookingRepository = bookingRepository;
        this.notificationRepository = notificationRepository;
    }

    public List<Approved> getAll(){
       return approvedRepository.findAll();
    }

    public Date secondDoseDate (Date date){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 14);
        Date secondDoseDate = c.getTime();
        return secondDoseDate;
    }

    @Transactional
    public void getSecondAppointment (long id){
        var optappoitment = approvedRepository.findById(id);
        if(optappoitment.isEmpty()){
            throw new ResourceNotFoundException();
        }
        var appoitment = optappoitment.get();
        var booking = appoitment.getBooking();
        var secondDate = secondDoseDate(booking.getDate());
        var user = booking.getUser();

        var newBooking = new Booking();
        newBooking.setDate(secondDate);
        newBooking.setTime(booking.getTime());
        newBooking.setVaccine(booking.getVaccine());
        newBooking.setUser(booking.getUser());
        newBooking.setVaccinationCenter(booking.getVaccinationCenter());
        newBooking.setStatus(Status.DONE);
        bookingRepository.save(newBooking);

        var notification = new Notification();
        notification.setUser(user);
        notification.setStatus("Your second dose date : " + dateFormat.format(newBooking.getDate()));

        var newAppoitment = new Approved();
        newAppoitment.setBooking(newBooking);

        notificationRepository.save(notification);
        approvedRepository.save(newAppoitment);
        approvedRepository.delete(appoitment);
    }

    public void deleteAppoitment (long id){
        var approved = approvedRepository.findById(id).get();
        var bookingId = approved.getBooking().getId();
        approvedRepository.deleteById(id);
        bookingRepository.deleteById(bookingId);
    }

}
