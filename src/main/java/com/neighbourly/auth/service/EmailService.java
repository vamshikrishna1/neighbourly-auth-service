package com.neighbourly.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service

public class EmailService {

    @Autowired
    private MailSender mailSender;


    public void sendEmail(String toEmail, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setText("OTP : " + text);
        message.setFrom("v4vamshikrishna@gmail.com");
        mailSender.send(message);
    }
}
