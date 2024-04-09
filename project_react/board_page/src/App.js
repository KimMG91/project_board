import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Login from "./pages/Login";
import SignUp from "./pages/SignUp";
import MyInfo from "./pages/MyInfo";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<SignUp />} />
        <Route path="/myinfo" element={<MyInfo />} />
        {/* 다른 경로에 대한 처리도 추가할 수 있습니다 */}
      </Routes>
    </Router>
  );
}

export default App;
