package com.neighbourly.auth.web;

import com.neighbourly.auth.dto.LoginRequest;
import com.neighbourly.auth.dto.UserDto;
import com.neighbourly.auth.key.RSAKeyManager;
import com.neighbourly.auth.dto.VerifyOtpRequest;
import com.neighbourly.auth.service.AuthService;
import com.nimbusds.jose.jwk.JWKSet;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AuthController {


    private final AuthService authService;

    private final RSAKeyManager rsaKeyManager;


    @PostMapping("/send-otp")
    public ResponseEntity<Void> sendOtp(@Validated @RequestBody UserDto request, @RequestHeader String uuid) {
        authService.sendOtp(request);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/verify-otp")
    public ResponseEntity<Map<String, String>> verifyOtp(@Validated @RequestBody VerifyOtpRequest request, @RequestHeader String uuid){
        Map<String, String> map = authService.verifyOtp(request, uuid);
        return ResponseEntity.status(HttpStatus.OK).body(map);
    }

    @GetMapping("/.well-known/jwks.json")
    public ResponseEntity<Map<String, Object>> jwks(){
        var jwkSet =  new JWKSet(rsaKeyManager.toPublicJwk());
        return ResponseEntity.ok(jwkSet.toJSONObject());
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginRequest request, @RequestHeader String uuid){
        String jwt = authService.login(request, uuid);
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, "Bearer "+jwt).build();
    }







}
