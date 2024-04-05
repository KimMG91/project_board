package com.project.project_board.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.project.project_board.Sevice.AuthService;

import Dto.LoginDto;
import Dto.ResponseDto;
import Dto.SignInDto;
import Dto.SignInResponseDto;
import Dto.SignUpDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;



@RestController
//@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired AuthService authService;

    @PostMapping("/signUp")
    public ResponseDto<?> signUp(@RequestBody SignUpDto requestBody) {
        ResponseDto<?> result = authService.signUp(requestBody);
        return result;
    }

    @PostMapping("/login")
    public ResponseDto<?> login(@RequestBody LoginDto requestBody) {
        ResponseDto<?> result = authService.login(requestBody);
        return result;
    }
    

    @PostMapping("/counselorSigln")
    public ResponseDto<?>counselorSigln(@RequestBody SignInDto requestBody) {
        ResponseDto<?> result = authService.counselorSigln(requestBody);
        return result;
    }
    
    @PostMapping("/managerSignl")
    public  ResponseEntity<?>managerSignln(@RequestBody SignInDto  requestBody) {
        ResponseDto<?> result = authService.managerSignln(requestBody);        
        return setToken(result);
    }
    
    //Respone 결과에 따라 Header에 Token설정
    private ResponseEntity<?> setToken(ResponseDto<?> result){
        //요청이 성공인 경우
        if(result.getResult()){
            // result -> data -> token추출
            SignInResponseDto signInResponse = (SignInResponseDto)result.getData();

            //Header에 Auth에 Token지정, Body에는 result그대로 작성(result 내의 token은 제거해도 될듯)
            return ResponseEntity.ok()
                        .header("Authorization","Bearer " + signInResponse.getToken())
                        .body(result);
        }else{
            return ResponseEntity.ok().body(result);
        }
    }
    
}
