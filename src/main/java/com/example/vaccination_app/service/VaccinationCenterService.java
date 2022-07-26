package com.example.vaccination_app.service;

import com.example.vaccination_app.exception.ResourceNotFoundException;
import com.example.vaccination_app.model.VaccinationCenter;
import com.example.vaccination_app.model.Vaccine;
import com.example.vaccination_app.repository.VaccinationCenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

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

    public Set<Vaccine> getVaccinesForCenter(long id){
        var optCenter = vaccinationCenterRepository.findById(id);
        if(optCenter.isEmpty()){
            throw new ResourceNotFoundException();
        }
        var center = optCenter.get();
        var vaccines = center.getVaccines();
        return vaccines;
    }
}
