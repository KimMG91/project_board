import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

function SignUp() {
  //HistoryRouterProps
  const navigate = useNavigate();

  // 폼 입력값을 관리할 상태 변수 선언 및 초기화
  const [formData, setFormData] = useState({
    email: "",
    password: "",
    confirmPassword: "",
    name: "",
    phoneNumber: "",
    userType: "",
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  // 회원가입 폼 제출 이벤트 핸들러 함수 정의
  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      // fetch API를 사용하여 서버에 POST 요청을 보냄
      const response = await fetch("http://localhost:8080/api/auth/signup", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(formData),
      });

      // 응답을 JSON 형식으로 변환하여 data 변수에 저장
      const data = await response.json();

      console.log(data);

      if (response.ok) {
        navigate("/login");
      }
    } catch (error) {
      console.error("Error:", error);
    }
  };

  // 회원가입 폼 렌더링
  return (
    <div>
      <form onSubmit={handleSubmit} style={{ padding: 50 }}>
        {/* 각 입력 필드에 대한 입력 요소 정의 */}
        <input
          type="email"
          name="email"
          value={formData.email}
          onChange={handleChange}
          placeholder="이메일"
          required
        />
        <br />
        <input
          type="password"
          name="password"
          value={formData.password}
          onChange={handleChange}
          placeholder="비밀번호"
          required
        />
        <br />
        <input
          type="password"
          name="confirmPassword"
          value={formData.confirmPassword}
          onChange={handleChange}
          placeholder="비밀번호 확인"
          required
        />
        <br />
        <input
          type="text"
          name="name"
          value={formData.name}
          onChange={handleChange}
          placeholder="이름"
          required
        />
        <br />
        <input
          type="tel"
          name="phoneNumber"
          value={formData.phoneNumber}
          onChange={handleChange}
          placeholder="전화번호"
          required
        />
        <br />
        <select
          style={{ width: 175, textAlign: "center" }}
          name="userType"
          onChange={handleChange}
          value={formData.userType}
          required
        >
          <option value="user">사용자</option>
          <option value="admin">관리자</option>
        </select>
        <button type="submit">회원가입</button>
      </form>
    </div>
  );
}

export default SignUp;
