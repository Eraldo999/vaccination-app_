package com.example.vaccination_app.service;

import com.example.vaccination_app.exception.ResourceAlreadyExistsException;
import com.example.vaccination_app.exception.ResourceNotFoundException;
import com.example.vaccination_app.model.Answers;
import com.example.vaccination_app.model.enums.AnswersStatus;
import com.example.vaccination_app.repository.AnswersRepository;
import com.example.vaccination_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Set;

@Service
public class AnswersService {
    private final AnswersRepository answersRepository;
    private final UserRepository userRepository;

    @Autowired
    public AnswersService(AnswersRepository answersRepository, UserRepository userRepository) {
        this.answersRepository = answersRepository;
        this.userRepository = userRepository;
    }

    public Answers getAnswersForUser (long id){
        var usr = userRepository.findById(id);
        if(usr.isEmpty()){
            throw new ResourceNotFoundException();
        }
        var user = usr.get();
        return user.getAnswers();
    }

    public void saveAnswersForUser (Principal principal, Answers answers){
        var usr = userRepository.findByEmail(principal.getName());
        if(usr.isEmpty()){
            throw new ResourceNotFoundException();
        }
            var user = usr.get();
            answers.setUser(user);
            answers.setStatus(AnswersStatus.COMPLETED);
            answersRepository.save(answers);

    }
}
