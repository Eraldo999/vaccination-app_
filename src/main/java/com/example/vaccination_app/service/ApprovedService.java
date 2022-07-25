package com.example.vaccination_app.service;

import com.example.vaccination_app.exception.ResourceNotFoundException;
import com.example.vaccination_app.model.Approved;
import com.example.vaccination_app.model.Booking;
import com.example.vaccination_app.repository.ApprovedRepository;
import com.example.vaccination_app.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ApprovedService {

    private final ApprovedRepository approvedRepository;
    private final BookingRepository bookingRepository;

    @Autowired
    public ApprovedService(ApprovedRepository approvedRepository, BookingRepository bookingRepository) {
        this.approvedRepository = approvedRepository;
        this.bookingRepository = bookingRepository;
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

    public void getSecondAppointment (long id){
        var optappoitment = approvedRepository.findById(id);
        if(optappoitment.isEmpty()){
            throw new ResourceNotFoundException();
        }
        var appoitment = optappoitment.get();
        var booking = appoitment.getBooking();
        var secondDate = secondDoseDate(booking.getDate());
        var newBooking = new Booking();
        newBooking.setDate(secondDate);
        newBooking.setLocation(booking.getLocation());
        newBooking.setVaccine(booking.getVaccine());
        newBooking.setUser(booking.getUser());
        newBooking.setVaccinationCenter(booking.getVaccinationCenter());
        newBooking.setApprove(true);
        bookingRepository.save(newBooking);
        var newAppoitment = new Approved();
        newAppoitment.setBooking(newBooking);
        approvedRepository.save(newAppoitment);
        approvedRepository.delete(appoitment);


    }
}
