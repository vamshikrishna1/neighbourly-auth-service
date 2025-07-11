package com.neighbourly.auth.store;


public interface CacheStore {

    void store(String key, String value);

    String get(String key);
}
