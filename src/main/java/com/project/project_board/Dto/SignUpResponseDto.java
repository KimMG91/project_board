package com.project.project_board.Dto;

public class SignUpResponseDto {
    private String token;
    private String hashedPassword;

    public SignUpResponseDto(String token, String hashedPassword) {
        this.token = token;
        this.hashedPassword = hashedPassword;
    }

    // Getters and setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }
}