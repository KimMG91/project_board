import React, { useState } from "react";
import { Link, useNavigate } from 'react-router-dom';

function Login() {
 //HistoryRouterProps
 const navigate = useNavigate();

  const [formData, setFormData] = useState({
    email: "",
    password: "",
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleLogin = async (e) => {
    e.preventDefault();
  
    const { email, password } = formData;
  
    try {
      const response = await fetch('http://localhost:8080/api/auth/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ email, password }),
      });
  
      if (!response.ok) {
        throw new Error('로그인에 실패했습니다.');
      }
  
      const data = await response.json();
  
      // 서버에서 반환한 응답에 따라 처리
      if (data.result) {
        // 로그인 성공시에는 토큰 등의 정보를 처리하거나 페이지를 이동하는 등의 작업을 수행할 수 있습니다.
        console.log('로그인 성공!');
        if (response.ok) {
          navigate("/myinfo");
        }
      } else {
        console.log('로그인 실패:', data.message);
      }
    } catch (error) {
      console.error('로그인 에러:', error.message);
    }
  };
  

  return (
    
    <div>
      <h2>로그인</h2>
      <form onSubmit={handleLogin}>
        <div>
          <label htmlFor="email">이메일:</label>
          <input type="email" id="email" name="email" value={formData.email} onChange={handleChange} required />
        </div>
        <div>
          <label htmlFor="password">비밀번호:</label>
          <input type="password" id="password" name="password" value={formData.password} onChange={handleChange} required />
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
