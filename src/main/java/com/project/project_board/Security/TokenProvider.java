package com.project.project_board.Security;

import com.nimbusds.jose.*;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import lombok.Data;

import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;

import java.time.Instant;
import java.util.Date;

import org.springframework.stereotype.Component;

@Data
@Component
public class TokenProvider {
    private static final String SECURITY_KEY = "inputYourSecurityKey";

    //JWT생성 메서드
    public String createJwt(String email, int duration){
        try{
            //현재 시간 기준 1시간 뒤로 만료시간 설정
            Instant now = Instant.now();
            Instant exprTime = now.plusSeconds(duration);

            //JWT Claim설정
            // *Claim 집합<< 내용설정 (페이로드 설정)
            //subject<< "sub", issuer << "iss", expiration time 
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(email)
                    .issueTime(Date.from(now))
                    .expirationTime(Date.from(exprTime))
                    .build();
            //JWT 서명
            SignedJWT signedJWT = new SignedJWT(
                new JWSHeader(JWSAlgorithm.HS256),
                claimsSet
            );

            //HWAC 서명을 사용하여 JWT서명
            JWSSigner signer = new MACSigner(SECURITY_KEY.getBytes());// *서명설정
            signedJWT.sign(signer);
            
            return signedJWT.serialize();
                    
        }catch(JOSEException e){
            return null;
        }
    }

    //JWT 검증 메서드
    public String validateJwt(String token){
        try {
            // 서명을 확인을 통한 JWT검증
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWSVerifier verifier = new MACVerifier(SECURITY_KEY.getBytes());
            if(signedJWT.verify(verifier)){
                return signedJWT.getJWTClaimsSet().getSubject();
            }else{
                // 서명이 유효하지 않은 경우
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    /*
        페이로드에 미리 정의된 Claim

        iss(issuer; 발행자),
        exp(expireation time; 만료 시간),
        sub(subject; 제목),
        iat(issued At; 발행 시간),
        jti(JWI ID)
     */
}
