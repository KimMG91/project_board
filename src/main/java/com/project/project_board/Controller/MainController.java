package com.project.project_board.Controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;


/*
 * React - Spring boot 연결을 확인하기 위해 테스트 코드 작성
 * React Cors 문제를 해결하기 위해 @CrossOrigin 어노테이션을 추가하였다. 본인의 React 포트가 다르다면 해당 포트 번호로 맞춰주어야 한다.
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/")
public class MainController {
    @GetMapping("check")
    public String check() {
        return "Success";
    }
    
}
