package com.example.vaccination_app.repository;

import com.example.vaccination_app.model.VaccinationCenter;
import com.example.vaccination_app.model.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VaccinationCenterRepository extends JpaRepository<VaccinationCenter, Long> {
}
