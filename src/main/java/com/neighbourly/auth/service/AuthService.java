package com.neighbourly.auth.service;

import com.neighbourly.auth.model.RegisterRequest;
import com.neighbourly.auth.store.OtpStore;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Random;

@Service
public class AuthService {

    @Autowired
    private OtpStore otpStore;

    @Autowired
    private EmailService emailService;

    public void sendOtp(RegisterRequest request) {
        String otp = generateOtp();
        otpStore.storeOtp(request.getEmail(), otp);
        emailService.sendEmail(request.getEmail(),otp);


        // TODO: Save OTP (in-memory/Redis)
        // TODO: Send OTP via SMS/Email (mock for now)
      //  System.out.println("Generated OTP for " + request.phoneOrEmail() + ": " + otp);
    }

    private String generateOtp() {
        return String.valueOf(new Random().nextInt(899999) + 100000);
    }


}
