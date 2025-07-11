package com.neighbourly.auth.store;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleStore implements CacheStore {

    private static final Map<String, String> otpMap  = new ConcurrentHashMap<>();


    @Override
    public void store(String email, String otp) {
        otpMap.put(email, otp);
    }

    @Override
    public String get(String email) {
        return otpMap.get(email);
    }
}
