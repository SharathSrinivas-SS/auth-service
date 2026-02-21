package com.example.auth.service;

import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PasswordService {
    public String hash(String password){
        return BcryptUtil.bcryptHash(password);
    }
    public Boolean matches(String password, String hashedPassword){
        return BcryptUtil.matches(password, hashedPassword);
    }
}
