package com.project.project_board.Sevice;

import java.util.Optional;

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
            // 사용자 id에 해당하는 정보 조회
            Optional<UserEntity> userOptional = userRepository.findById(email);
            if (!userOptional.isPresent()) {
                return ResponseDto.setFailed("입력하신 이메일로 가입된 계정이 없습니다.");
            }

            UserEntity userEntity = userOptional.get();

            // 데이터베이스에 저장된 암호화된 비밀번호와 클라이언트에서 입력한 비밀번호를 비교
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            if (!passwordEncoder.matches(password, userEntity.getPassword())) {
                return ResponseDto.setFailed("비밀번호가 일치하지 않습니다.");
            }

            // 비밀번호는 반환하지 않도록 비워줍니다.
            userEntity.setPassword("");

            // 토큰 생성
            int exprTime = 360; // 10분
            String token = tokenProvider.createJwt(email, exprTime);
            if (token == null) {
                return ResponseDto.setFailed("토큰 생성에 실패하였습니다.");
            }

            // 로그인 응답 DTO에 토큰 추가하여 생성
            LoginResponseDto loginResponseDto = new LoginResponseDto(token, exprTime, userEntity);

            // 로그인 성공 메시지와 함께 로그인 응답 DTO 반환
            return ResponseDto.setSuccessData("로그인에 성공하였습니다.", loginResponseDto);
        } catch (Exception e) {
            // 예외 발생 시 실패 메시지 반환
            return ResponseDto.setFailed("로그인 중 오류가 발생하였습니다.");
        }
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
