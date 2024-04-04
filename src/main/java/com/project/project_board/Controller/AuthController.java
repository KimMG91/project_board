package com.project.project_board.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.project.project_board.Sevice.AuthService;

import Dto.ResponseDto;
import Dto.SignUpDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
//@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired AuthService authService;
    @PutMapping("/signUp")
    public ResponseDto<?>signUp(@RequestBody SignUpDto requestBody) {
        //System.out.println(requestBody.toString());
        ResponseDto<?> result = authService.signUp(requestBody);
        return result;
    }
}
