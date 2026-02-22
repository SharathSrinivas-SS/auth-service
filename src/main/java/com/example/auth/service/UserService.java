package com.example.auth.service;

import com.example.auth.model.User;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class UserService {
    public void createUser(String email, String password) {
        User user = new User();
        user.email = email;
        user.password = password;
        user.roles = List.of("USER");
        user.persist();
    }

    public User findByEmail(String email) {
        return User.find("email", email).firstResult();
    }

    public boolean existByEmail(String email){
        return User.find("email",email).firstResult() != null;
    }
}
