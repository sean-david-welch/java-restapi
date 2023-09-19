package com.example.demo.config;
import com.example.demo.user.UserRepository;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
public class ApplicationConfig {

     @Value("${SECRET_KEY}")
    private String secretKey;

    @PostConstruct
    public void validate() {
        if (secretKey == null || secretKey.isEmpty()) {
            throw new IllegalArgumentException("SECRET_KEY must not be null or empty");
        }
    }

    @Bean
    public String secretKey() {
        return secretKey;
    }

    private final UserRepository repository;

    public ApplicationConfig(final UserRepository repository) {
        this.repository = repository;
    }

    @Bean
    UserDetailsService userDetailsService() {
        return username -> repository.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found" + username));
    }    
}
