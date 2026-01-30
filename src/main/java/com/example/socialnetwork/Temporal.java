package com.example.socialnetwork;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Temporal {
    public static void main(String[] args) {
//        System.out.println(Encoders.BASE64.encode(Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded()));
//        byte[] decoded = java.util.Base64.getDecoder().decode("rpoeVRlEjaXO0YvU7sn1mfhZ7RJm8GtjYvWA1vbz+bQ=");
//        System.out.println(decoded.length); // imprime los bytes reales
        var encoder = new BCryptPasswordEncoder();
        String hash = encoder.encode("password123");
        System.out.println(hash);

    }
}
