package com.project.project_board.Dto;

import com.project.project_board.Entity.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDto {
    private String token;
    private int exprTime;
    private String userType;
    private String phoneNumber ;
    private UserEntity userEntity;

    // // 생성자 정의
    // public LoginResponseDto(String token, int exprTime, UserEntity userEntity, String userType, String phoneNumber) {
    //     this.token = token;
    //     this.exprTime = exprTime;
    //     this.userEntity = userEntity;
    //     this.userType = userType; // userType을 UserEntity에서 가져옵니다.
    //     this.phoneNumber = phoneNumber; // userType을 UserEntity에서 가져옵니다.
    // }
}
