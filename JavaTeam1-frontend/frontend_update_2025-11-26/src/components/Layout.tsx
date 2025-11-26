// src/layout/Layout.tsx

import { Outlet, Link } from "react-router-dom";

export default function Layout() {
  return (
    <div style={{ minHeight: "100vh", display: "flex", flexDirection: "column" }}>
      
      {/* HEADER */}
      <header
        style={{
          padding: "12px 20px",
          borderBottom: "1px solid #ddd",
          background: "#f9f9f9",
        }}
      >
        <h2 style={{ margin: 0 }}>생활 법률 도우미</h2>

        <nav style={{ marginTop: "10px", display: "flex", gap: "16px" }}>
          <Link to="/home">홈</Link>
          <Link to="/search">상황 검색</Link>
          <Link to="/mypage">마이페이지</Link>
        </nav>
      </header>

      {/* CONTENT AREA */}
      <main style={{ flex: 1, padding: "20px" }}>
        <Outlet />
      </main>

      {/* FOOTER */}
      <footer
        style={{
          padding: "14px",
          textAlign: "center",
          borderTop: "1px solid #ddd",
          background: "#f9f9f9",
          marginTop: "40px",
        }}
      >
        © 2025 생활 법률·권리 안내 도우미
      </footer>
    </div>
  );
}
