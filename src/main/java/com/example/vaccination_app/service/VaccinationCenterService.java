package com.example.vaccination_app.service;

import com.example.vaccination_app.model.VaccinationCenter;
import com.example.vaccination_app.repository.VaccinationCenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VaccinationCenterService {

    private final VaccinationCenterRepository vaccinationCenterRepository;

    @Autowired
    public VaccinationCenterService(VaccinationCenterRepository vaccinationCenterRepository) {
        this.vaccinationCenterRepository = vaccinationCenterRepository;
    }

    public List<VaccinationCenter> getAllVaccinationCenters(){
        return vaccinationCenterRepository.findAll();
    }
}
