package com.neighbourly.auth.dto;

import lombok.Data;

@Data
public class Response<T> {
    private String uuid;
    private T data;
    private boolean success;
}
