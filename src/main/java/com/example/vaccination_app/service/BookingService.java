package com.example.vaccination_app.service;

import com.example.vaccination_app.dto.BookingCreateDto;
import com.example.vaccination_app.exception.ResourceNotFoundException;
import com.example.vaccination_app.model.Booking;
import com.example.vaccination_app.repository.BookingRepository;
import com.example.vaccination_app.repository.UserRepository;
import com.example.vaccination_app.repository.VaccineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final VaccineRepository vaccineRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository, UserRepository userRepository, VaccineRepository vaccineRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.vaccineRepository = vaccineRepository;
    }

    public List<Booking> getAllBookings (){
        return bookingRepository.findAll();
    }

    @Transactional
    public void createBooking (BookingCreateDto req, Principal principal, long vaccineId){
        var optUser = userRepository.findByEmail(principal.getName());
        var optVaccine = vaccineRepository.findById(vaccineId);
        if(optUser.isEmpty() || optVaccine.isEmpty()){
            throw new ResourceNotFoundException();
        }
        var user = optUser.get();
        var vaccine = optVaccine.get();

        var booking = new Booking();
        booking.setDate(req.getDate());
        booking.setLocation(req.getLocation());
        booking.setVaccine(vaccine);
        booking.setUser(user);

        bookingRepository.save(booking);
    }
}
