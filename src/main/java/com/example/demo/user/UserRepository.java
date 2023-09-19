package com.example.demo.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<CustomUserDetails, String> {
    Optional<CustomUserDetails> findByEmail(String email);
}
