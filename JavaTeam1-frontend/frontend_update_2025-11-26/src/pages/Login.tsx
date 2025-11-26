// src/pages/Login.tsx
import { useState } from "react";
import { useNavigate } from "react-router-dom";

export default function Login() {
  const navigate = useNavigate();
  const [id, setId] = useState("");
  const [pw, setPw] = useState("");

  const handleLogin = () => {
    if (!id.trim() || !pw.trim()) {
      alert("아이디와 비밀번호를 입력하세요.");
      return;
    }

    // 로그인 성공 → token 저장
    // 실제 백엔드를 사용하지 않으므로 id 자체를 토큰처럼 저장
    localStorage.setItem("token", id);

    alert("로그인 성공!");

    // 홈 화면으로 이동
    navigate("/home");
  };

  return (
    <div style={{ maxWidth: 400, margin: "40px auto", padding: 20 }}>
      <h2 style={{ textAlign: "center", marginBottom: 30 }}>로그인</h2>

      <input
        type="text"
        placeholder="아이디"
        value={id}
        onChange={(e) => setId(e.target.value)}
        style={{ padding: 12, width: "100%", marginBottom: 15 }}
      />

      <input
        type="password"
        placeholder="비밀번호"
        value={pw}
        onChange={(e) => setPw(e.target.value)}
        style={{ padding: 12, width: "100%", marginBottom: 20 }}
      />

      <button
        onClick={handleLogin}
        style={{
          width: "430px",
          padding: 12,
          background: "#0a6bbf",
          color: "#fff",
          borderRadius: 8,
          cursor: "pointer",
        }}
      >
        로그인
      </button>

      <p style={{ marginTop: 20, textAlign: "center" }}>
        계정이 없나요?{" "}
        <span
          onClick={() => navigate("/signup")}
          style={{ color: "#0a6bbf", cursor: "pointer" }}
        >
          회원가입
        </span>
      </p>
    </div>
  );
}
