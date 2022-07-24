package com.example.vaccination_app.repository;

import com.example.vaccination_app.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

//    @Query (insert)
//    void addVaccineToBooking(long vaccineId);
}
