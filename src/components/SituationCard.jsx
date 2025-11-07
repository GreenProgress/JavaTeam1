export default function SituationCard({ title, description, categori, active, updatedAt }) {
  return (
    <div
      style={{
        border: "1px solid #ddd",
        borderRadius: "12px",
        padding: "16px",
        marginBottom: "12px",
        background: "#fff",
      }}
    >
      <h3 style={{ margin: 0 }}>{title}</h3>
      <p style={{ margin: "8px 0 12px" }}>{description}</p>
      <div style={{ fontSize: 12, color: "#666" }}>
        <div>분류: {categori || "미정"}</div>
        <div>상태: {active ? "활성" : "비활성"}</div>
        <div>수정일: {updatedAt ? new Date(updatedAt).toLocaleString() : "-"}</div>
      </div>
    </div>
  );
}
