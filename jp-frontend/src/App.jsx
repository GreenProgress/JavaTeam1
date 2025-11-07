import Router from "./router/Router";

export default function App() {
  return (
    <>
      <header style={{ background: "#2a5d84", color: "#fff", textAlign: "center", padding: 12 }}>
        <h1 style={{ margin: 0, fontSize: 20 }}>생활 법률·권리 안내 도우미</h1>
      </header>
      <Router />
    </>
  );
}
