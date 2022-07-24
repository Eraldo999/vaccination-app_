package com.example.vaccination_app.service;

import com.example.vaccination_app.model.Vaccine;
import com.example.vaccination_app.repository.VaccineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VaccineService {

    VaccineRepository vaccineRepository;

    @Autowired
    public VaccineService(VaccineRepository vaccineRepository) {
        this.vaccineRepository = vaccineRepository;
    }

    public List<Vaccine> getAllVaccines() {
        return vaccineRepository.findAll();
    }

    public Optional<Vaccine> getVaccineById(long id) {
        return vaccineRepository.findById(id);
    }

    public void save (Vaccine vaccine){
        vaccineRepository.save(vaccine);
    }

    public void deleteVaccineById (long id){
        vaccineRepository.deleteById(id);
    }


}
