package com.example.api.configuration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.example.api.domain.User;
import com.example.api.repositories.UserRepository;

@Configuration
@Profile("local")
public class LocalConfig {
    
    @Autowired
    private UserRepository repository;

    @Bean
    public void startDB() {
        User u1 = new User(null, "Diego","123", "diego@teste.com");
        User u2 = new User(null, "Daniel","123", "daniel@teste.com");

        repository.saveAll(List.of(u1,u2));

    }
}
