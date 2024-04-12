package com.project.project_board.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.project.project_board.Dto.LoginDto;
import com.project.project_board.Dto.LoginResponseDto;
import com.project.project_board.Dto.ResponseDto;
import com.project.project_board.Dto.SignInDto;
import com.project.project_board.Dto.SignUpDto;
import com.project.project_board.Security.TokenProvider;
import com.project.project_board.Sevice.AuthService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/api/auth")//접속경로
public class AuthController {
    @Autowired
    AuthService authService;
    @Autowired
    TokenProvider tokenProvider;

    @PostMapping("/signup")//회원가입 컨트롤러
    public ResponseEntity<?> signUp(@RequestBody SignUpDto requestBody) {
        ResponseDto<?> result = authService.signUp(requestBody);

        // 회원가입이 성공했을 경우
        if (result.getData() != null) {
            return ResponseEntity.ok().body(setToken(result));
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }

    @PostMapping("/login")//로그인 컨트롤러
    public ResponseEntity<?> login(@RequestBody LoginDto requestBody) {
        ResponseDto<?> result = authService.login(requestBody);
         // 로그인성공했을 경우
         if (result.getData() != null) {
            return ResponseEntity.ok().body(setToken(result));
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }
    
    @PostMapping("/counselorSignIn")
    public ResponseDto<?> counselorSignIn(@RequestBody SignInDto requestBody) {
        ResponseDto<?> result = authService.counselorSignIn(requestBody);
        return result;
    }

    @PostMapping("/managerSignIn")
    public ResponseEntity<?> managerSignIn(@RequestBody SignInDto requestBody) {
        ResponseDto<?> result = authService.managerSignIn(requestBody);
        return setToken(result);
    }

    // Response 결과에 따라 Header에 Token 설정
    private ResponseEntity<?> setToken(ResponseDto<?> result) {
    // 요청이 성공한 경우
    if (result.getResult()) {
        // result -> data -> token 추출
        if (result.getData() instanceof LoginResponseDto) {
            LoginResponseDto loginResponse = (LoginResponseDto) result.getData();
            String token = loginResponse.getToken();
            
            // Header에 Auth에 토큰 지정, Body에는 result 그대로 작성
            return ResponseEntity.ok()
                    .header("Authorization", "Bearer " + token)
                    .body(result);
        } else {
            // 타입이 다를 경우 예외를 처리하거나 다른 응답을 반환
            return ResponseEntity.badRequest().body("Unexpected data type");
        }
    } else {
        return ResponseEntity.badRequest().body(result);
    }
}

}
