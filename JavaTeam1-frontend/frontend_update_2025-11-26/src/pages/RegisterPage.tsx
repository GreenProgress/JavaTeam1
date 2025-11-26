// src/pages/RegisterPage.tsx
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import "./Auth.css";

export default function RegisterPage() {
  const navigate = useNavigate();
  const [form, setForm] = useState({
    userId: "",
    password: "",
    confirmPw: "",
    nickname: "",
  });

  const handleChange = (e: any) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleRegister = async () => {
    if (form.password !== form.confirmPw) {
      alert("비밀번호가 일치하지 않습니다.");
      return;
    }

    try {
      const res = await axios.post("http://localhost:8080/auth/register", {
        userId: form.userId,
        password: form.password,
        nickname: form.nickname,
      });

      if (res.data.success) {
        alert("회원가입 성공!");
        navigate("/login");
      } else {
        alert(res.data.message);
      }
    } catch (err) {
      alert("회원가입 실패");
    }
  };

  return (
    <div className="auth-container">
      <div className="auth-card">
        <h2>회원가입</h2>

        <input
          name="userId"
          type="text"
          placeholder="아이디"
          value={form.userId}
          onChange={handleChange}
        />

        <input
          name="password"
          type="password"
          placeholder="비밀번호"
          value={form.password}
          onChange={handleChange}
        />

        <input
          name="confirmPw"
          type="password"
          placeholder="비밀번호 확인"
          value={form.confirmPw}
          onChange={handleChange}
        />

        <input
          name="nickname"
          type="text"
          placeholder="닉네임"
          value={form.nickname}
          onChange={handleChange}
        />

        <button className="login-btn" onClick={handleRegister}>
          회원가입
        </button>
      </div>
    </div>
  );
}
