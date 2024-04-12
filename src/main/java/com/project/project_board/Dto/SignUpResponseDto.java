package com.project.project_board.Dto;

public class SignUpResponseDto {
    private String hashedPassword;

    public SignUpResponseDto(String token, String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

  

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }
}