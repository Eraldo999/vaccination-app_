package com.example.vaccination_app.service;

import com.example.vaccination_app.exception.ResourceNotFoundException;
import com.example.vaccination_app.model.Vaccine;
import com.example.vaccination_app.repository.UserRepository;
import com.example.vaccination_app.repository.VaccineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class VaccineService {

    VaccineRepository vaccineRepository;
    UserRepository userRepository;

    @Autowired
    public VaccineService(VaccineRepository vaccineRepository, UserRepository userRepository) {
        this.vaccineRepository = vaccineRepository;
        this.userRepository = userRepository;
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

    public Set<Vaccine> getVaccinesForUser(long id){
        var user = userRepository.findById(id);
        if(user.isEmpty()){
            throw new ResourceNotFoundException();
        }
        var usr = user.get();
        return usr.getVaccinationCenter().getVaccines();
    }


}
