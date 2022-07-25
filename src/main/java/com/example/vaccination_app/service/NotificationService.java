package com.example.vaccination_app.service;

import com.example.vaccination_app.exception.ResourceNotFoundException;
import com.example.vaccination_app.model.Notification;
import com.example.vaccination_app.repository.NotificationRepository;
import com.example.vaccination_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository, UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    public List<Notification> getAllNotificationForUser (Principal principal){
        var optUser = userRepository.findByEmail(principal.getName());
        if(optUser.isEmpty()){
            throw new ResourceNotFoundException();
        }
        var user = optUser.get();
        return notificationRepository.getNotificationByUser_Id(user.getId());
    }
}
