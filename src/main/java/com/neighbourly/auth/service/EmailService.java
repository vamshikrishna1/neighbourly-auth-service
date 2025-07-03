package com.neighbourly.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service

public class EmailService {

    @Autowired
    private MailSender mailSender;


    public void sendEmail(String toEmail, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setText(text);
        message.setSubject(subject);

        mailSender.send(message);
    }
}
