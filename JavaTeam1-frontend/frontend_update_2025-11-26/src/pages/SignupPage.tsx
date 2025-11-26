// src/pages/SignupPage.tsx
import { useState } from "react";
import { useNavigate } from "react-router-dom";

export default function SignupPage() {
  const [id, setId] = useState("");
  const [pw, setPw] = useState("");
  const [pwCheck, setPwCheck] = useState("");
  const navigate = useNavigate();

  const handleSignup = () => {
    if (!id || !pw || !pwCheck) {
      alert("모든 항목을 입력하세요.");
      return;
    }

    if (pw !== pwCheck) {
      alert("비밀번호가 일치하지 않습니다.");
      return;
    }

    // 기존 유저 목록 불러오기
    const existingUsers = JSON.parse(localStorage.getItem("users") || "[]");

    // 아이디 중복 체크
    if (existingUsers.some((user: any) => user.id === id)) {
      alert("이미 존재하는 아이디입니다.");
      return;
    }

    // 새 유저 추가
    const newUser = { id, pw };
    const updatedUsers = [...existingUsers, newUser];

    // localStorage 저장
    localStorage.setItem("users", JSON.stringify(updatedUsers));

    alert("회원가입이 완료되었습니다!");
    navigate("/login");
  };

  return (
    <div style={{ padding: "20px" }}>
      <h2>회원가입</h2>

      <input
        placeholder="아이디"
        value={id}
        onChange={(e) => setId(e.target.value)}
        style={{ display: "block", margin: "10px 0", padding: "8px" }}
      />

      <input
        type="password"
        placeholder="비밀번호"
        value={pw}
        onChange={(e) => setPw(e.target.value)}
        style={{ display: "block", margin: "10px 0", padding: "8px" }}
      />

      <input
        type="password"
        placeholder="비밀번호 확인"
        value={pwCheck}
        onChange={(e) => setPwCheck(e.target.value)}
        style={{ display: "block", margin: "10px 0", padding: "8px" }}
      />

      <button onClick={handleSignup} style={{ padding: "10px 20px" }}>
        회원가입
      </button>

      <p style={{ marginTop: "20px" }}>
        이미 계정이 있으신가요?{" "}
        <span
          style={{ color: "blue", cursor: "pointer" }}
          onClick={() => navigate("/login")}
        >
          로그인
        </span>
      </p>
    </div>
  );
}
