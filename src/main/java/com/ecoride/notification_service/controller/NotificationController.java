package com.ecoride.notification_service.controller;

import com.ecoride.notification_service.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private EmailService emailService;

    // PATH CHANGE KIYA: 'send-ride-update' ko 'send-update' kar diya taaki Client se match ho
    @PostMapping("/send-update")
    public String sendUpdate(@RequestParam String email, @RequestParam String message) {
        emailService.sendEmail(email, "EcoRide - Ride Status Update", message);
        return "Notification sent successfully!";
    }
}