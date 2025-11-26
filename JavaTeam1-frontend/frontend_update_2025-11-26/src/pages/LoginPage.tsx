// src/pages/LoginPage.tsx
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import "./Auth.css";

export default function LoginPage() {
  const navigate = useNavigate();
  const [userId, setUserId] = useState("");
  const [password, setPassword] = useState("");

  const handleLogin = async () => {
    try {
      const res = await axios.post("http://localhost:8080/auth/login", {
        userId,
        password,
      });

      if (res.data.success) {
        alert("로그인 성공!");
        localStorage.setItem("token", res.data.userId); 
        navigate("/home");
      } else {
        alert("아이디 또는 비밀번호가 틀렸습니다.");
      }
    } catch (err) {
      alert("로그인 실패");
    }
  };

  return (
    <div className="auth-container">
      <div className="auth-card">
        <h2>로그인</h2>

        <input
          type="text"
          placeholder="아이디"
          value={userId}
          onChange={(e) => setUserId(e.target.value)}
        />

        <input
          type="password"
          placeholder="비밀번호"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />

        <button className="login-btn" onClick={handleLogin}>
          로그인
        </button>

        <button
          className="signup-btn"
          onClick={() => navigate("/register")}
        >
          회원가입
        </button>
      </div>
    </div>
  );
}
