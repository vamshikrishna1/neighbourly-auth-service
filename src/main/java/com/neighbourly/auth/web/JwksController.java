package com.neighbourly.auth.web;

import com.neighbourly.auth.key.RSAKeyManager;
import com.nimbusds.jose.jwk.JWKSet;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class JwksController {

    private RSAKeyManager rsaKeyManager;


    public ResponseEntity<Map<String, Object>> jwks() {
        JWKSet jwkSet = new JWKSet(rsaKeyManager.toPublicJwk());
        return ResponseEntity.ok(jwkSet.toJSONObject());
    }



}
