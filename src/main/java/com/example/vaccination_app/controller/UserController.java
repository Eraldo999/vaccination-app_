package com.example.vaccination_app.controller;

import com.example.vaccination_app.dto.UserCreateDto;
import com.example.vaccination_app.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@Slf4j
@RequestMapping
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/~")
    public String userHome(@AuthenticationPrincipal User user) {
        var res = user.getAuthorities()
                .stream()
                .filter(auth -> auth.getAuthority().equals("ROLE_ADMIN"))
                .findAny();

        if (res.isPresent()) {
            log.debug("Admin {}, logged in. Redirecting to /admin/", user.getUsername());
            return "redirect:/index";
        }
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String index (){
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
        if(bindingResult.hasErrors()){
            return "/register";
        }
        else{
            userService.register(usr);
            return "redirect:/";
        }

    }
}
