package com.neighbourly.auth.web;

import com.neighbourly.auth.model.RegisterRequest;
import com.neighbourly.auth.model.VerifyOtpRequest;
import com.neighbourly.auth.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Validated @RequestBody RegisterRequest request, @RequestHeader String uuid) {
        authService.sendOtp(request);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@Validated @RequestBody VerifyOtpRequest request, @RequestHeader String uuid){
        authService.verifyOtp(request, uuid);
        return ResponseEntity.ok("OTP VERIFICATION SUCCESSFUL");
    }






}
