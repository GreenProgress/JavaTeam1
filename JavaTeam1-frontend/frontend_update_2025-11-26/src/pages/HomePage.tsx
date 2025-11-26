// src/pages/HomePage.tsx
import { useNavigate } from "react-router-dom";

export default function HomePage() {
  const navigate = useNavigate();

  return (
    <div
      style={{
        padding: 20,
        display: "flex",
        justifyContent: "center",
        marginTop: 50,
      }}
    >
      <div
        style={{
          width: 320,
          padding: "30px 20px",
          borderRadius: 12,
          background: "#ffffff",
          boxShadow: "0 3px 10px rgba(0,0,0,0.1)",
          display: "flex",
          flexDirection: "column",
          gap: 15,
        }}
      >
        <h2 style={{ textAlign: "center", marginBottom: 10 }}>메뉴 선택</h2>

        {/* 상황 검색 */}
        <button
          onClick={() => navigate("/search")}
          style={btnStyle}
        >
          상황 검색
        </button>

        {/* 법령 검색 (다음 단계에서 구현) */}
        <button
          onClick={() => navigate("/lawsearch")}
          style={btnStyle}
        >
          법령 검색
        </button>

        {/* 로그인/회원가입 */}
        <button
          onClick={() => navigate("/login")}
          style={btnStyle}
        >
          로그인 / 회원가입
        </button>

        {/* 마이페이지 (다음 단계에서 구현) */}
        <button
          onClick={() => navigate("/mypage")}
          style={btnStyle}
        >
          마이페이지
        </button>
      </div>
    </div>
  );
}

const btnStyle: React.CSSProperties = {
  padding: "14px",
  borderRadius: 8,
  border: "1px solid #ccc",
  background: "#f7f7f7",
  cursor: "pointer",
  fontSize: 16,
};
