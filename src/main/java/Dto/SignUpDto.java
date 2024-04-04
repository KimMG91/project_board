package Dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.print.DocFlavor.STRING;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class SignUpDto {
    private String id;
    private String email;
    private String name;
    private String password;
    private String cofirmPassword;
    private String phoneNumber;
    private String userType;
    private LocalDateTime createdAt;
    private LocalDateTime editedAt;
    private LocalDateTime lastLoginAt;
    private String token;
}
