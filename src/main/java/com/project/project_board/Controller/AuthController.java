package com.project.project_board.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.project.project_board.Dto.LoginDto;
import com.project.project_board.Dto.ResponseDto;
import com.project.project_board.Dto.SignInDto;
import com.project.project_board.Dto.SignInResponseDto;
import com.project.project_board.Dto.SignUpDto;
import com.project.project_board.Sevice.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpDto requestBody) {
        ResponseDto<?> result = authService.signUp(requestBody);

        // 회원가입이 성공했을 경우
        if (result.getData() != null) {
            return ResponseEntity.ok().body(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto requestBody) {
        ResponseDto<?> result = authService.login(requestBody);
        return ResponseEntity.ok().body(result);
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
        // 요청이 성공인 경우
        if (result.getResult()) {
            // reulst -> data -> token 추출
            SignInResponseDto signInResponse = (SignInResponseDto) result.getData();

            // Header에 Auth에 Token 지정, Body에는 result 그대로 작성 (result 내의 token은 제거해도 될듯)
            return ResponseEntity.ok()
                    .header("Authorization", "Bearer " + signInResponse.getToken())
                    .body(result);
        } else {
            return ResponseEntity.ok().body(result);
        }
    }
}
