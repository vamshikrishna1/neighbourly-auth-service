package com.neighbourly.auth.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {


    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

}
