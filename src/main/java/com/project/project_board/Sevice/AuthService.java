package com.project.project_board.Sevice;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.project_board.Dto.LoginDto;
import com.project.project_board.Dto.LoginResponseDto;
import com.project.project_board.Dto.ResponseDto;
import com.project.project_board.Dto.SignInDto;
import com.project.project_board.Dto.SignUpDto;
import com.project.project_board.Entity.UserEntity;
import com.project.project_board.Repository.UserRepository;
import com.project.project_board.Security.TokenProvider;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenProvider tokenProvider;

    @Transactional
    public ResponseDto<?> signUp(SignUpDto dto) {
        String email = dto.getEmail();
        String password = dto.getPassword();
        String confirmPassword = dto.getConfirmPassword();

        // email(id) 중복 확인
        try {
            if (userRepository.existsById(email)) {
                return ResponseDto.setFailed("중복된 Email 입니다.");
            }
        } catch (Exception e) {
            return ResponseDto.setFailed("데이터베이스 연결에 실패하였습니다.");
        }

        // 비밀번호 암호화 및 비교
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password); // 클라이언트 쪽에서 가져온 비밀번호, 비밀번호 확인 둘다 암호화해서 비교하려고 하면 안됌,

        // 비밀번호 일치 여부 확인
        if (!password.equals(confirmPassword)) {
            return ResponseDto.setFailed("비밀번호가 일치하지 않습니다.");
        }

        // 회원 엔티티 생성 및 저장
        UserEntity userEntity = new UserEntity(dto);
        userEntity.setPassword(hashedPassword);

        try {
            // 회원가입 엔티티에 저장
            userRepository.save(userEntity);

            // 비밀번호 제거하여 반환 , 암호화된 비밀번호만 넘기고 기존 입력했던 password정보는 제거
            dto.setPassword(null);
            dto.setConfirmPassword(null);

            return ResponseDto.setSuccessData("회원 생성에 성공했습니다.", dto);
        } catch (Exception e) {
            return ResponseDto.setFailed("회원 생성에 실패하였습니다.");
        }

    }

    public ResponseDto<LoginResponseDto> login(LoginDto dto) {
        String email = dto.getEmail();
        String password = dto.getPassword();

        try {
            // 사용자 id/password 일치하는지 확인
            boolean existed = userRepository.existsByEmailAndPassword(email, password);
            if (!existed) {
                return ResponseDto.setFailed("입력하신 로그인 정보가 존재하지 않습니다.");
            }
        } catch (Exception e) {
            return ResponseDto.setFailed("데이터베이스 연결에 실패하였습니다.");
        }

        UserEntity userEntity = null;

        try {
            // 값이 존재하는 경우 사용자 정보 불러옴 (기준 email)
            userEntity = userRepository.findById(email).get();
        } catch (Exception e) {
            return ResponseDto.setFailed("데이터베이스 연결에 실패하였습니다.");
        }

        userEntity.setPassword("");

        int exprTime = 3600; // 1h
        String token = tokenProvider.createJwt(email, exprTime);

        if (token == null) {
            return ResponseDto.setFailed("토큰 생성에 실패하였습니다.");
        }

        LoginResponseDto loginResponseDto = new LoginResponseDto(token, exprTime, userEntity);

        return ResponseDto.setSuccessData("로그인에 성공하였습니다.", loginResponseDto);
    }

    public ResponseDto<?> managerSignIn(SignInDto requestBody) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'managerSignIn'");
    }

    public ResponseDto<?> counselorSignIn(SignInDto requestBody) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'counselorSignIn'");
    }

}
