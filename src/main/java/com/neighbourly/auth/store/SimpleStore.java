package com.neighbourly.auth.store;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SimpleStore implements  OtpStore {

    private static final Map<String, String> otpMap  = new ConcurrentHashMap<>();


    @Override
    public void storeOtp(String email, String otp) {
        otpMap.put(email, otp);
    }

    @Override
    public String getOtp(String email) {
        return otpMap.get(email);
    }
}
