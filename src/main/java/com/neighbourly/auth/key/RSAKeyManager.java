package com.neighbourly.auth.key;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.RSAKey;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;


import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

@Getter
@Component
public class RSAKeyManager {

    private final RSAPrivateKey privateKey;
    private final RSAPublicKey publicKey;

    private final String keyId;

    public RSAKeyManager() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        var keyPair = keyPairGenerator.generateKeyPair();
        this.privateKey = (RSAPrivateKey) keyPair.getPrivate();
        this.publicKey = (RSAPublicKey) keyPair.getPublic();
        this.keyId="kid-08-2025" ;
    }

    public RSAKey toPublicJwk(){
        return new RSAKey.Builder(this.publicKey)
                .algorithm(JWSAlgorithm.RS256)
                .keyID(this.keyId)
                .build();
    }

}
