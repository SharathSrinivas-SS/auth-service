package com.example.auth.model;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;

import java.util.List;

@MongoEntity(collection = "users")
public class User extends PanacheMongoEntity {
    public String email;
    public String password;
    public List<String> roles;
}
