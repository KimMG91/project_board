import React, { useEffect, useState } from "react";
import { jwtDecode } from "jwt-decode"; // JWT 토큰을 파싱하기 위해 라이브러리 사용

function MyInfo() {
  // 사용자 정보 및 토큰 만료 시간 상태
  const [userInfo, setUserInfo] = useState();
  const [tokenExpiry, setTokenExpiry] = useState(null);

  // 컴포넌트가 마운트될 때 로컬 스토리지에서 토큰을 가져와 사용자 정보를 파싱합니다.
  useEffect(() => {
    // 로컬 스토리지에서 토큰을 가져옵니다.
    const token = localStorage.getItem("authToken");

    if (token) {
      // 토큰을 파싱합니다.
      const decodedToken = jwtDecode(token);
       // 토큰에서 userType 클레임을 추출합니다.
       const userType = decodedToken.userType;
       const phoneNumber = decodedToken.phoneNumber;
      console.log("Decoded token:", decodedToken);

      // 사용자 정보와 토큰 만료 시간을 상태에 저장합니다.
      setUserInfo({
        userType: userType, // 토큰에서 이름을 추출
        phoneNumber: phoneNumber, // 토큰에서 이름을 추출
        email: decodedToken.sub, // 토큰에서 이메일 (subject)을 추출
      });

      // 토큰 만료 시간은 `exp` 클레임에서 추출합니다.
      setTokenExpiry(decodedToken.exp);
    }
  }, []);

  return (
    <div>
      <h2>내 정보</h2>
      {/* 사용자 정보 및 토큰 만료 시간 표시 */}
      {userInfo ? (
        <div>
          <p>권한: {userInfo.userType}</p>
          <p>이메일: {userInfo.email}</p>
          <p>토큰 만료 시간: {new Date(tokenExpiry * 1000).toLocaleString()}</p>
        </div>
      ) : (
        <p>사용자 정보를 불러오는 중입니다...</p>
      )}
    </div>
  );
}

export default MyInfo;
