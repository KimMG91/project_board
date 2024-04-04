package com.project.project_board.Sevice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.project_board.Entity.UserEntity;
import com.project.project_board.Repository.UserRepository;

import Dto.ResponseDto;
import Dto.SignInResponseDto;
import Dto.SignUpDto;

@Service
public class AuthService {
    @Autowired UserRepository userRepository;
    public ResponseDto<?> signUp(SignUpDto dto){
        String email = dto.getEmail();
        String password =dto.getPassword();
        String confirmPassword = dto.getCofirmPassword();

        //email(id)중복 확인
        try {
            //존재하는 경우 : true /존재하지 않는 경우 : false
            if (userRepository.existsById(email)) {
                return ResponseDto.setFailed("중복된 Email 입니다.");
            } 
        } catch (Exception e) {
            return ResponseDto.setFailed("데이터베이스 연결에 실패하였습니다.");
        }

        //password 중복 확인
        if(!password.equals(confirmPassword)){
            return ResponseDto.setFailed("비밀번호가 일치하지 않습니다.");
        }

        //UserEntity생성
        UserEntity userEntity = new UserEntity(dto);

         //
         BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
         String hashedPassword = userEntity.getPassword();

         boolean isPasswordMatch = passwordEncoder.matches(password, hashedPassword);
 
         if(!isPasswordMatch){
            return ResponseDto.setFailed("암호화 실패했습니다.");
         }

         userEntity.setPassword(hashedPassword);

        //UserRepository를 이용하여 DB에 entity저장
        try {
            userRepository.save(userEntity);
        } catch (Exception e) {
            return ResponseDto.setFailed("데이터베이스 연결에 실패하였습니다.");
        }
        return  ResponseDto.setSuccess("회원 생성에 성공했습니다.");
       
    }

    public ResponseDto<SignInResponseDto> singin(SignUpDto dto){
        String email = dto.getEmail();
        String password = dto.getPassword();
        UserEntity userEntity;

        try {
            //이메일로 사용자 정보 가져오기
            userEntity = userRepository.findById(email).orElse(null);
            if (userEntity == null) {
                return ResponseDto.setFailed("입력하신 이메일로 등록된 계정이 존재하지 않습니다.");
            }

            //사용자가 입력한 비밀번호를 BCryptPasswordEncoder를 사용하여 암호화
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodeedPassword = userEntity.getPassword();

            //저장된 암호화된 비밀번호롸 입력된 암호화된 비밀번호 비교
            if (!passwordEncoder.matches(password, encodeedPassword)) {
                return ResponseDto.setFailed("비밀번호가 일치하지 않습니다.");                
            }
        } catch (Exception e) {
            return ResponseDto.setFailed("데이터베이스 연결에 실패하였습니다.");
        }

        //Client에 비밀번호 제공 방지
        userEntity.setPassword("");
        String name = userEntity.getName();


        return ResponseDto.setSuccessData("로그인에 성공하였습니다.", loginResponseDto);
    }

}
