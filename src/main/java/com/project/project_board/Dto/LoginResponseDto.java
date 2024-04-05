package com.project.project_board.Dto;

import com.project.project_board.Entity.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDto {
    private String Token;
    private int exprTime;
    private UserEntity user;
}
