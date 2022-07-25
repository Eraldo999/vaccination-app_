package com.example.vaccination_app.controller;

import com.example.vaccination_app.dto.UserCreateDto;
import com.example.vaccination_app.dto.UserUpdateDto;
import com.example.vaccination_app.service.UserService;
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

    @Autowired
    public UserController(UserService userService, VaccineService vaccineService) {
        this.userService = userService;
        this.vaccineService = vaccineService;
    }

    @GetMapping("/index")
    public String index() {
        return "/index";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        var user = new UserCreateDto();
        model.addAttribute("user", user);
        return "/register";
    }

    @PostMapping("/register")
    @ResponseBody
    public String register(
            @Valid @ModelAttribute("user") UserCreateDto usr,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "/register";
        } else {
            userService.register(usr);
            return "redirect:/";
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


}
