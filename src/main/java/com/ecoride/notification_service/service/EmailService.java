package com.ecoride.notification_service.service;

import com.ecoride.notification_service.model.Notification;
import com.ecoride.notification_service.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.MimeMessageHelper;


@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private NotificationRepository notificationRepository;

    public void sendEmail(String to, String subject, String text) {
        Notification log = new Notification();
        log.setRecipientEmail(to);
        log.setMessageContent(text);
        log.setSentAt(LocalDateTime.now());

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            // Professional HTML Template
            String htmlMsg = "<div style='font-family: Arial, sans-serif; border: 1px solid #ddd; padding: 20px; border-radius: 10px;'>" +
                    "<h2 style='color: #2e7d32;'>🚗 EcoRide Booking Confirmed!</h2>" +
                    "<p>Hello User,</p>" +
                    "<p>Your ride has been successfully booked. Here are your trip details:</p>" +
                    "<div style='background-color: #f9f9f9; padding: 15px; border-radius: 5px;'>" +
                    "<p><strong>Trip Details:</strong> " + text + "</p>" +
                    "</div>" +
                    "<p style='margin-top: 20px;'>Thank you for choosing <strong>EcoRide</strong> for a greener planet!</p>" +
                    "<hr style='border: 0; border-top: 1px solid #eee;'>" +
                    "<p style='font-size: 12px; color: #888;'>This is an automated message. Please do not reply.</p>" +
                    "</div>";

            helper.setText(htmlMsg, true); // 'true' ka matlab HTML enable hai
            helper.setTo(to);
            helper.setSubject("Trip Confirmation - EcoRide 🚗");
            helper.setFrom("sourabhmahajan1111@gmail.com");

            mailSender.send(mimeMessage);
            log.setStatus("SENT");
        } catch (Exception e) {
            log.setStatus("FAILED");
            System.out.println("Error: " + e.getMessage());
        }
        notificationRepository.save(log);
    }
}