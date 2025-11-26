// src/components/Header.jsx
export default function Header() {
  return (
    <header style={styles.header}>
      <h1 style={styles.title}>생활 법률·권리 안내 도우미</h1>
    </header>
  );
}

const styles = {
  header: {
    position: "sticky",
    top: 0,
    zIndex: 1000,
    width: "100vw",   // ★ 화면 전체 너비 강제 적용
    left: 0,          // ★ 혹시 모를 offset 방지
    background: "#2a5d84",
    color: "#fff",
    textAlign: "center",
    padding: "16px 0",
    boxShadow: "0 2px 6px rgba(0,0,0,0.18)",
  },
  title: {
    margin: 0,
    fontSize: "20px",
    fontWeight: "600",
  },
};
