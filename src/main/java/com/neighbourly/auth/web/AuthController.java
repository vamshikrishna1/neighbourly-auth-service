package com.neighbourly.auth.web;

import com.neighbourly.auth.model.RegisterRequest;
import com.neighbourly.auth.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Validated @RequestBody RegisterRequest request, @RequestHeader(required = true) String uuid) {
        authService.sendOtp(request);
        return ResponseEntity.ok().build();
    }

}
