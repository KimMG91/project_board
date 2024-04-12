import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";

function Login() {
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    email: "",
    password: "",
    userType: "",
    phoneNumber:"",

  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleLogin = async (e) => {
    e.preventDefault();

    const { email, password, userType, phoneNumber } = formData;

    try {
      const response = await fetch("http://localhost:8080/api/auth/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ email, password ,phoneNumber, userType}),
      });

      console.log("response.ok:", response.ok);

      if (!response.ok) {
        throw new Error("로그인에 실패했습니다.");
      }

      const data = await response.json();
      console.log("응답 데이터 정보:", data);

      // 로그인 성공 여부 확인
      if (response.ok) {
        console.log("로그인 성공!");

        // `body.data.token` 경로를 따라 `token` 값을 추출합니다.
        const token = data?.body?.data?.token;

        // `token` 값이 `undefined`인지 확인합니다.
        if (token) {
          console.log("토큰:", token);

          // 로컬 스토리지에 `token` 값을 저장합니다.
          localStorage.setItem("authToken", token,email,userType,phoneNumber);
          console.log("localStro", localStorage)

          // 페이지 이동
          navigate("/myinfo");
        } else {
          console.error("토큰을 찾을 수 없습니다. 응답 데이터:", data);
        }
      } else {
        console.error("로그인 실패:", data.message);
      }
    } catch (error) {
      console.error("로그인 에러:", error.message);
    }
  };

  return (
    <div>
      <h2>로그인</h2>
      <form onSubmit={handleLogin}>
        <div>
          <label htmlFor="email">이메일:</label>
          <input
            type="email"
            id="email"
            name="email"
            value={formData.email}
            onChange={handleChange}
            required
          />
        </div>
        <div>
          <label htmlFor="password">비밀번호:</label>
          <input
            type="password"
            id="password"
            name="password"
            value={formData.password}
            onChange={handleChange}
            required
          />
        </div>
        <button type="submit">로그인</button>
      </form>

      <p>
        계정이 없으신가요? <Link to="/signup">회원가입</Link>
      </p>
    </div>
  );
}

export default Login;
