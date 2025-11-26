// src/components/Footer.jsx
export default function Footer() {
  return (
    <footer style={styles.footer}>
      <p style={styles.text}>© 2025 생활 법률·권리 안내 도우미</p>
    </footer>
  );
}

const styles = {
  footer: {
    backgroundColor: "#2a5d84",
    color: "#fff",
    textAlign: "center",
    padding: "20px 10px",
    marginTop: "40px",
  },
  text: {
    margin: "0",
    fontSize: "14px",
  },
};
