package com.example.vaccination_app.service;

import com.example.vaccination_app.model.Questions;
import com.example.vaccination_app.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {
    private QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<Questions> getAllQuestions (){
        return questionRepository.findAll();
    }

    public Questions getQuestions (long id){
        return (questionRepository.findById(id)).get();
    }
}
