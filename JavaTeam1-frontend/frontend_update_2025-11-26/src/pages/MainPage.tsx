// src/pages/MainPage.tsx
import { useNavigate } from "react-router-dom";

export default function MainPage() {
  const navigate = useNavigate();

  return (
    <div style={{ padding: 20 }}>

      {/* 상단 타이틀 */}
      <div
        style={{
          background: "#2a5d84",
          color: "white",
          textAlign: "center",
          padding: "22px 12px",
          borderRadius: 10,
          fontSize: 22,
          fontWeight: 600,
          marginBottom: 28,
          boxShadow: "0 2px 6px rgba(0,0,0,0.12)",
        }}
      >
        생활법률·권리 안내 도우미
      </div>

      {/* 핵심 기능 카드 3개 */}
      <div style={{ display: "flex", flexDirection: "column", gap: 16 }}>
        {/* 상황 검색 */}
        <div
          onClick={() => navigate("/search")}
          style={{
            padding: "18px 20px",
            background: "white",
            borderRadius: 10,
            cursor: "pointer",
            fontSize: 18,
            fontWeight: 600,
            boxShadow: "0 2px 6px rgba(0,0,0,0.08)",
            transition: "transform 0.15s ease, box-shadow 0.15s ease",
          }}
          onMouseEnter={(e) => {
            (e.currentTarget as HTMLDivElement).style.transform = "translateY(-3px)";
            (e.currentTarget as HTMLDivElement).style.boxShadow =
              "0 4px 12px rgba(0,0,0,0.15)";
          }}
          onMouseLeave={(e) => {
            (e.currentTarget as HTMLDivElement).style.transform = "translateY(0)";
            (e.currentTarget as HTMLDivElement).style.boxShadow =
              "0 2px 6px rgba(0,0,0,0.08)";
          }}
        >
          📝 상황으로 찾기
        </div>

        {/* 법령 검색 */}
        <div
          onClick={() => navigate("/law-search")}
          style={{
            padding: "18px 20px",
            background: "white",
            borderRadius: 10,
            cursor: "pointer",
            fontSize: 18,
            fontWeight: 600,
            boxShadow: "0 2px 6px rgba(0,0,0,0.08)",
            transition: "transform 0.15s ease, box-shadow 0.15s ease",
          }}
          onMouseEnter={(e) => {
            (e.currentTarget as HTMLDivElement).style.transform = "translateY(-3px)";
            (e.currentTarget as HTMLDivElement).style.boxShadow =
              "0 4px 12px rgba(0,0,0,0.15)";
          }}
          onMouseLeave={(e) => {
            (e.currentTarget as HTMLDivElement).style.transform = "translateY(0)";
            (e.currentTarget as HTMLDivElement).style.boxShadow =
              "0 2px 6px rgba(0,0,0,0.08)";
          }}
        >
          📚 법령으로 찾기
        </div>

        {/* 전체 상황 목록 */}
        <div
          onClick={() => navigate("/situations")}
          style={{
            padding: "18px 20px",
            background: "white",
            borderRadius: 10,
            cursor: "pointer",
            fontSize: 18,
            fontWeight: 600,
            boxShadow: "0 2px 6px rgba(0,0,0,0.08)",
            transition: "transform 0.15s ease, box-shadow 0.15s ease",
          }}
          onMouseEnter={(e) => {
            (e.currentTarget as HTMLDivElement).style.transform = "translateY(-3px)";
            (e.currentTarget as HTMLDivElement).style.boxShadow =
              "0 4px 12px rgba(0,0,0,0.15)";
          }}
          onMouseLeave={(e) => {
            (e.currentTarget as HTMLDivElement).style.transform = "translateY(0)";
            (e.currentTarget as HTMLDivElement).style.boxShadow =
              "0 2px 6px rgba(0,0,0,0.08)";
          }}
        >
          📄 전체 상황 목록
        </div>
      </div>

      {/* 하단 아이콘 메뉴 (6개) */}
      <div
        style={{
          marginTop: 35,
          display: "grid",
          gridTemplateColumns: "repeat(3, 1fr)",
          gap: 20,
          textAlign: "center",
        }}
      >
        <div onClick={() => navigate("/search")} style={iconStyle}>
          🔍 <br /> 상황검색
        </div>

        <div onClick={() => navigate("/law-search")} style={iconStyle}>
          📘 <br /> 법령검색
        </div>

        <div onClick={() => navigate("/mypage")} style={iconStyle}>
          👤 <br /> 마이페이지
        </div>

        <div onClick={() => navigate("/login")} style={iconStyle}>
          🔑 <br /> 로그인
        </div>

        <div onClick={() => navigate("/signup")} style={iconStyle}>
          📝 <br /> 회원가입
        </div>

        <div onClick={() => navigate("/situations")} style={iconStyle}>
          📂 <br /> 목록
        </div>
      </div>
    </div>
  );
}

/* 공통 아이콘 스타일 */
const iconStyle: React.CSSProperties = {
  background: "white",
  padding: "20px 10px",
  borderRadius: 10,
  boxShadow: "0 2px 6px rgba(0,0,0,0.08)",
  cursor: "pointer",
  fontSize: 16,
  fontWeight: 600,
  lineHeight: 1.4,
  transition: "transform 0.15s ease, box-shadow 0.15s ease",
};
