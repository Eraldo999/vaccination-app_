package com.example.vaccination_app.controller;

import com.example.vaccination_app.dto.UserCreateDto;
import com.example.vaccination_app.dto.UserUpdateDto;
import com.example.vaccination_app.exception.ResourceNotFoundException;
import com.example.vaccination_app.model.VaccinationCenter;
import com.example.vaccination_app.service.UserService;
import com.example.vaccination_app.service.VaccinationCenterService;
import com.example.vaccination_app.service.VaccineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@Slf4j
//@RequestMapping
public class UserController {

    private UserService userService;
    private VaccineService vaccineService;
    private VaccinationCenterService vaccinationCenterService;

    @Autowired
    public UserController(UserService userService, VaccineService vaccineService, VaccinationCenterService vaccinationCenterService) {
        this.userService = userService;
        this.vaccineService = vaccineService;
        this.vaccinationCenterService = vaccinationCenterService;
    }

    @GetMapping("/index")
    public String index() {
        return "/index";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        var user = new UserCreateDto();
        model.addAttribute("user", user);
        var centers = vaccinationCenterService.getAllVaccinationCenters();
        model.addAttribute("centers", centers);
        return "/register";
    }

    @PostMapping("/register")
    @ResponseBody
    public String register(
            @Valid @ModelAttribute("user") UserCreateDto usr,
            BindingResult bindingResult, Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "/register";
        } else {
            userService.register(usr);
            return "redirect:/login";
        }
    }

    @GetMapping("/")
    public String showHome(Model model, Principal principal) {
        var usr = userService.getUserByPrincipal(principal);
        if (usr.isPresent()) {
            model.addAttribute("user", usr.get());
            return "user/account";
        }

        log.error("No user with username {} can be found", principal.getName());
        return "error/404";
    }

//    @GetMapping("/updateUser-form")
//    public String update (@RequestParam("id") long id, Model model){
//        var optUser = userService.getUserById(id);
//        var user = optUser.get();
//        model.addAttribute("user", user);
//        return "user/update-user.html";
//    }

    @PostMapping("/save-user")
    public String save(@ModelAttribute("user") com.example.vaccination_app.model.User user) {
        userService.saveUser(user);
        return "redirect:/index";
    }

    @GetMapping("/update-user/{id}")
    public String getUpdateUser(@PathVariable(value = "id") long id, Model model) {
        var user = userService.getUserById(id);

        var usr = UserUpdateDto.fromEntity(user.get());

        model.addAttribute("user", usr);

        return "user/update-user";
    }

    @PostMapping("/update-user")
    public String postUpdateTeam(
            @ModelAttribute("user") UserUpdateDto req,
            Principal principal
    ) {
        userService.updateUser(req, principal);
        return "redirect:/";
    }

    @GetMapping("/vaccine-details")
    public String vaccineDetails(Model model){
        var vaccines = vaccineService.getAllVaccines();
        model.addAttribute("vaccines", vaccines);
        return "user/vaccine-details";
    }

    @GetMapping("/select-center")
    public String selectVaccinationCenter (Model model, Principal principal){
        var vaccinationCenters = vaccinationCenterService.getAllVaccinationCenters();
        var user = userService.getUserByPrincipal(principal);
        if(user.isEmpty()){
            throw new ResourceNotFoundException();
        }
        var usr = user.get();
        model.addAttribute("user", usr);
        model.addAttribute("vaccinationCenters", vaccinationCenters);

        return "user/select-vaccinationCenter";
    }

    @RequestMapping("/select-centerr/{id}")
    public String selectVaccinationCenterr (@PathVariable long id, Principal principal){
        var user = (userService.getUserByPrincipal(principal));
        if (user.isEmpty()){
            throw new ResourceNotFoundException();
        }
        var userId = (user.get()).getId();

        userService.setUserVaccinationCenter(userId,id);

        return "redirect:/";
    }


}
