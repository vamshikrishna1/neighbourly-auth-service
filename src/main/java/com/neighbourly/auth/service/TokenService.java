package com.neighbourly.auth.service;

import com.neighbourly.auth.key.RSAKeyManager;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final RSAKeyManager rsaKeyManager;
    @Value("${app.jwt.issuer}")
    private String issuer;
    @Value("${app.jwt.audience}")
    private String audience;

    @Value("${app.jwt.expiration}")
    private long expiration;


    public String generateToken(Long userId, Set<String> roles){
        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .claim("scope", getRoles(roles))
                .setIssuer(issuer)
                .setAudience(audience)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusSeconds(expiration)))
                .setHeaderParam("kid", rsaKeyManager.getKeyId())
                .signWith(rsaKeyManager.getPrivateKey(), SignatureAlgorithm.RS256)
                .compact();

    }

    private String getRoles(Set<String> roles){
        return String.join(" ", roles);
    }








}
