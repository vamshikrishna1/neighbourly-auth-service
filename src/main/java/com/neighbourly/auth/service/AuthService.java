package com.neighbourly.auth.service;

import com.neighbourly.auth.constants.AppConstants;
import com.neighbourly.auth.model.RegisterRequest;
import com.neighbourly.auth.store.OtpStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.neighbourly.auth.constants.AppConstants.*;

import java.security.SecureRandom;


@Service
public class AuthService {

    @Autowired
    private OtpStore otpStore;

    @Autowired
    private EmailService emailService;

    public void sendOtp(RegisterRequest request) {
        String otp = generateOtp();
        otpStore.storeOtp(request.getEmail(), otp);
        emailService.sendEmail(request.getEmail(), OTP_EMAIL_SUBJECT, OTP_EMAIL_TEMPLATE.formatted(request.getFirstName(), otp));
    }

    private String generateOtp() {
        return String.valueOf(new SecureRandom().nextInt(99999) + 100000);
    }


}
