package com.example.vaccination_app.service;

import com.example.vaccination_app.config.SecurityConfig;
import com.example.vaccination_app.dto.UserCreateDto;
import com.example.vaccination_app.dto.UserUpdateDto;
import com.example.vaccination_app.exception.ResourceNotFoundException;
import com.example.vaccination_app.model.User;
import com.example.vaccination_app.model.VaccinationCenter;
import com.example.vaccination_app.repository.ApplicationRoleRepository;
import com.example.vaccination_app.repository.UserRepository;
import com.example.vaccination_app.repository.VaccinationCenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.Optional;

@Service
public class UserService {

    private ApplicationRoleRepository applicationRoleRepository;
    private UserRepository userRepository;
    private VaccinationCenterRepository vaccinationCenterRepository;

    @Autowired
    public UserService(ApplicationRoleRepository applicationRoleRepository, UserRepository userRepository, VaccinationCenterRepository vaccinationCenterRepository) {
        this.applicationRoleRepository = applicationRoleRepository;
        this.userRepository = userRepository;
        this.vaccinationCenterRepository = vaccinationCenterRepository;
    }


    @Transactional
    public void register (UserCreateDto req) {
        var user = User.fromCreateRequest(req);
        var optionalroleUser = applicationRoleRepository.findById(SecurityConfig.ROLE_USER_ID);
        var optCenter = vaccinationCenterRepository.findById(req.getVaccinationCenterId());
        if (optionalroleUser.isPresent() && optCenter.isPresent()) {
            var roleUser = optionalroleUser.get();
            user.setApplicationRole(roleUser);
            user.setVaccinationCenter(optCenter.get());
        }
        userRepository.save(user);
    }

    public Optional<User> getUserById (long id){
        return userRepository.findById(id);
    }

    public void saveUser (User user){
        userRepository.save(user);
    }

    public Optional<User> getUserByPrincipal (Principal principal){
        return userRepository.findByEmail(principal.getName());
    }

    public void updateUser (UserUpdateDto req, Principal principal){

        var optuser = userRepository.findById(req.getId());

        if(optuser.isPresent()){
            var user = optuser.get();
            user.update(req);
            userRepository.save(user);
        }
    }

}
