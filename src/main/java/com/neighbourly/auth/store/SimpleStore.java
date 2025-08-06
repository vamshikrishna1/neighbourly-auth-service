package com.neighbourly.auth.store;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleStore implements CacheStore {

    private static final Map<String, String> otpMap  = new ConcurrentHashMap<>();


    @Override
    public void store(String key, String value) {
        otpMap.put(key, value);
    }

    @Override
    public String get(String key) {
        return otpMap.get(key);
    }

    public void delete(String key) {
        otpMap.remove(key);
    }
}
