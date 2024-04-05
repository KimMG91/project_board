package com.project.project_board.Sevice;

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

    public ResponseDto<?> signUp(SignUpDto dto) {

        String email = dto.getEmail();
        String password = dto.getPassword();
        String confirmPassword = dto.getCofirmPassword();

        // email(id)중복 확인
        try {
            // 존재하는 경우 : true /존재하지 않는 경우 : false
            if (userRepository.existsById(email)) {
                return ResponseDto.setFailed("중복된 Email 입니다.");
            }
        } catch (Exception e) {
            return ResponseDto.setFailed("데이터베이스 연결에 실패하였습니다.");
        }

        // password 중복 확인
        if (!password.equals(confirmPassword)) {
            return ResponseDto.setFailed("비밀번호가 일치하지 않습니다.");
        }

        // UserEntity생성
        UserEntity userEntity = new UserEntity(dto);

        // 비밀번호 암호화
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);

        boolean isPasswordMatch = passwordEncoder.matches(password, hashedPassword);

        if (!isPasswordMatch) {
            return ResponseDto.setFailed("암호화 실패했습니다.");
        }

        userEntity.setPassword(hashedPassword);

        // UserRepository를 이용하여 DB에 entity저장
        try {
            userRepository.save(userEntity);
        } catch (Exception e) {
            return ResponseDto.setFailed("데이터베이스 연결에 실패하였습니다.");
        }

        return ResponseDto.setSuccess("회원 생성에 성공했습니다.");

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

    // public ResponseDto<SignInResponseDto> signIn(SignInDto dto) {
    //     String email = dto.getEmail();
    //     String password = dto.getPassword();
    //     UserEntity userEntity;

    //     try {
    //         // 이메일로 사용자 정보 가져오기
    //         userEntity = userRepository.findById(email).orElse(null);
    //         if (userEntity == null) {
    //             return ResponseDto.setFailed("입력하신 이메일로 등록된 계정이 존재하지 않습니다.");
    //         }

    //         // 사용자가 입력한 비밀번호를 BCryptPasswordEncoder를 사용하여 암호화
    //         BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    //         String encodedPassword = userEntity.getPassword();

    //         // 저장된 암호화된 비밀번호와 입력된 암호화된 비밀번호 비교
    //         if (!passwordEncoder.matches(password, encodedPassword)) {
    //             return ResponseDto.setFailed("비밀번호가 일치하지 않습니다.");
    //         }
    //     } catch (Exception e) {
    //         return ResponseDto.setFailed("데이터베이스 연결에 실패하였습니다.");
    //     }

    //       // Client에 비밀번호 제공 방지
    //       userEntity.setPassword("");
    //       String name = userEntity.getName();
        

    //       //생략된 코드 넣기!!
    //       LoginResponseDto loginResponseDto = new LoginResponseDto(token, exprTime, userEntity);
          

    //     return ResponseDto.setSuccessData("로그인에 성공하였습니다.", loginResponseDto);
    // }
}
