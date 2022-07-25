package com.example.vaccination_app.controller;

import com.example.vaccination_app.exception.ResourceNotFoundException;
import com.example.vaccination_app.service.NotificationService;
import com.example.vaccination_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/notification")
public class NotificationController {
    private final NotificationService notificationService;
    private final UserService userService;

    @Autowired
    public NotificationController(NotificationService notificationService, UserService userService) {
        this.notificationService = notificationService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String getNotifications(Principal principal, Model model){

        var notifications = notificationService.getAllNotificationForUser(principal);
        model.addAttribute("notifications", notifications);
        return "user/notification";
    }

}
