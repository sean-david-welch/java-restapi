package com.example.demo.data;

public class LoginResponseDTO {
    private UserResponseDTO user;
    private String jwt;

    public LoginResponseDTO() {
    }

    public LoginResponseDTO(UserResponseDTO user, String jwt) {
        this.user = user;
        this.jwt = jwt;
    }

    public UserResponseDTO getUser() {
        return user;
    }

    public void setUser(UserResponseDTO user) {
        this.user = user;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

}
