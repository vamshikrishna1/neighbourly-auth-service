package com.neighbourly.auth.store;


public interface OtpStore {

    void storeOtp(String email, String otp);

    String getOtp(String email);
}
