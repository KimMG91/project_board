import React, { useState } from 'react';

function App() {
  // 폼 입력값을 관리할 상태 변수 선언 및 초기화
  const [formData, setFormData] = useState({
    email: '',
    password: '',
    confirmPassword: '', // confirmPassword 필드 추가
    name: '',
    phoneNumber: '',
    userType: ''
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };
  

  // 회원가입 폼 제출 이벤트 핸들러 함수 정의
  const handleSubmit = async (e) => {
    e.preventDefault();

    // 비밀번호와 비밀번호 확인이 일치하는지 확인
    if (formData.password !== formData.confirmPassword) {
      alert('비밀번호가 일치하지 않습니다.');
      return;
    }
    try {
      // fetch API를 사용하여 서버에 POST 요청을 보냄
      const response = await fetch('http://localhost:8080/api/auth/signup', {
        method: 'POST', // HTTP 메서드: POST
        headers: {
          'Content-Type': 'application/json' // 요청 헤더 JSON 형식
        },
        body: JSON.stringify(formData) // 요청 본문에 폼 데이터를 JSON 형식으로 변환하여 설정
      });

      // 응답을 JSON 형식으로 변환하여 data 변수에 저장
      const data = await response.json();

      // 서버 응답 데이터 콘솔 출력
      console.log(data);
    } catch (error) {
      // 오류가 발생한 경우 콘솔에 오류 메세지 출력
      console.error('Error:', error);
    }
  };

  // 회원가입 폼 렌더링
  return (
    <form onSubmit={handleSubmit}>
      {/* 각 입력 필드에 대한 입력 요소 정의 */}
      <input type="email" name="email" value={formData.email} onChange={handleChange} placeholder="Email" required />
      <input type="password" name="password" value={formData.password} onChange={handleChange} placeholder="Password" required />
      <input type="password" name="confirmPassword" value={formData.confirmPassword} onChange={handleChange} placeholder="Confirm Password" required />
      <input type="text" name="name" value={formData.name} onChange={handleChange} placeholder="Name" required />
      <input type="tel" name="phoneNumber" value={formData.phoneNumber} onChange={handleChange} placeholder="PhoneNumber" required />
      <input type="text" name="userType" value={formData.userType} onChange={handleChange} placeholder="UserType" required />
      <button type="submit">회원가입</button>
    </form>
  );
}

export default App;
