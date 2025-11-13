import React from "react"; // [추가] React 임포트

/**
 * SituationCard 컴포넌트
 *
 * props를 { situation, onClick }으로 변경하여 받습니다.
 * situation 객체에서 데이터를 해체(destructure)하여 사용하고,
 * div 전체에 onClick 이벤트를 연결합니다.
 */
export default function SituationCard({ situation, onClick }) {
  
  // situation 객체에서 필요한 값들을 구조 분해합니다.
  const { title, description, category } = situation;

  return (
    <div
      style={{
        border: "1px solid #ddd",
        borderRadius: "12px",
        padding: "16px",
        marginBottom: "12px",
        background: "#fff",
        cursor: "pointer", // 클릭 가능하다는 것을 표시
      }}
      onClick={onClick} // Home.jsx에서 전달받은 onClick 이벤트 연결
    >
      <h3 style={{ margin: 0 }}>{title}</h3>
      <p style={{ margin: "8px 0 12px" }}>{description}</p>
      <div style={{ fontSize: 12, color: "#666" }}>
        <div>분류: {category || "미정"}</div>
        
      </div>
    </div>
  );
}