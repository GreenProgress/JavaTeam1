// src/pages/Signup.tsx
import { useState } from "react";
import { useNavigate } from "react-router-dom";

export default function Signup() {
  const navigate = useNavigate();
  const [id, setId] = useState("");
  const [pw, setPw] = useState("");

  const handleSignup = () => {
    if (!id.trim() || !pw.trim()) {
      alert("아이디와 비밀번호를 입력하세요.");
      return;
    }

    const newUser = { id, pw };
    localStorage.setItem("signupUser", JSON.stringify(newUser));

    alert("회원가입 완료! 로그인해 주세요.");
    navigate("/login");
  };

  return (
    <div style={{ maxWidth: 400, margin: "40px auto", padding: 20 }}>
      <h2 style={{ textAlign: "center", marginBottom: 30 }}>회원가입</h2>

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
        onClick={handleSignup}
        style={{
          width: "430px",
          padding: 12,
          background: "#0a6bbf",
          color: "#fff",
          borderRadius: 8,
          cursor: "pointer",
        }}
      >
        회원가입
      </button>

      <p style={{ marginTop: 20, textAlign: "center" }}>
        이미 계정이 있나요?{" "}
        <span
          onClick={() => navigate("/login")}
          style={{ color: "#0a6bbf", cursor: "pointer" }}
        >
          로그인
        </span>
      </p>
    </div>
  );
}
