package com.example.vaccination_app.controller;

import com.example.vaccination_app.model.Answers;
import com.example.vaccination_app.model.Questions;
import com.example.vaccination_app.service.AnswersService;
import com.example.vaccination_app.service.QuestionService;
import com.example.vaccination_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Objects;

@Controller
@RequestMapping("/answers")
public class AnswersController {

    private final AnswersService answersService;
    private final UserService userService;
    private final QuestionService questionService;

    @Autowired
    public AnswersController(AnswersService answersService, UserService userService, QuestionService questionService) {
        this.answersService = answersService;
        this.userService = userService;
        this.questionService = questionService;
    }

    @GetMapping("/")
    public String form(Model model, Principal principal) {
        var answers = new Answers();
        var user = (userService.getUserByPrincipal(principal)).get();
        model.addAttribute("answers", answers);
        model.addAttribute("questions", questionService.getQuestions(1L));
        model.addAttribute("user", user);
        return "user/answers";
    }

    @RequestMapping("/save")
    public String saveAnswers(@Valid @ModelAttribute("answers") Answers answers,
                              BindingResult bindingResult,
                              Principal principal) {

        if (bindingResult.hasErrors()) {
            return "redirect:/answers/";
        }
        var user = (userService.getUserByPrincipal(principal)).get();
        if (Objects.isNull(user.getAnswers())) {
            answersService.saveAnswersForUser(principal, answers);
            return "redirect:/booking/new";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/get-answers")
    public String getAnswersForUser(@RequestParam("id") long id, Model model) {
        model.addAttribute("questions", questionService.getQuestions(1L));
        model.addAttribute("answers", answersService.getAnswersForUser(id));
        return "admin/get-answers";
    }


}
