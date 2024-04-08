import React, { useState } from "react";
import { Link } from 'react-router-dom'; // React Router의 Link 컴포넌트를 사용하여 페이지 이동

function Login() {
  // 상태 변수를 사용하여 입력값을 관리합니다.
  const [formData, setFormData] = useState({
    email: "",
    password: "",
  });

  // 입력값이 변경될 때마다 상태를 업데이트하는 함수입니다.
  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  // 로그인 버튼 클릭 시 수행되는 함수입니다.
  const handleLogin = (e) => {
    e.preventDefault(); // 기본 제출 이벤트 방지

    // 입력된 이메일과 비밀번호를 가지고 로그인 요청을 서버에 보낼 수 있습니다.
    const { email, password } = formData;
    // 이후에는 서버로 요청을 보내는 코드를 작성하면 됩니다.
    console.log(`Email: ${email}, Password: ${password}`);
  };

  return (
    <div>
      <h2>로그인</h2>
      {/* 로그인 폼 */}
      <form onSubmit={handleLogin}> {/* 폼 제출 시 handleLogin 함수 호출 */}
        {/* 이메일 입력란 */}
        <div>
          <label htmlFor="email">이메일:</label>
          <input type="email" id="email" name="email" value={formData.email} onChange={handleChange} required />
        </div>
        {/* 비밀번호 입력란 */}
        <div>
          <label htmlFor="password">비밀번호:</label>
          <input type="password" id="password" name="password" value={formData.password} onChange={handleChange} required />
        </div>
        {/* 로그인 버튼 */}
        <button type="submit">로그인</button>
      </form>

      {/* 회원가입 버튼 */}
      <p>
        계정이 없으신가요? <Link to="/signup">회원가입</Link>
      </p>
    </div>
  );
}

export default Login;
