package com.example.demo.data;

import com.example.demo.user.User;

public record LoginResponseDTO(User user, String jwt) {

}
