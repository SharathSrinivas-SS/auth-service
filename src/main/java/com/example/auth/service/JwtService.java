package com.example.auth.service;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Set;

@ApplicationScoped
public class JwtService {
    public String generateToken(String userId, String email){
        return Jwt.issuer("auth-service")
                .subject(userId)
                .claim("email", email)
                .groups(Set.of("USER"))
                .expiresIn(3600)
                .sign();
    }
}
