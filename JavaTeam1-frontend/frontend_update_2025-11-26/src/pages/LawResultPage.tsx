// src/pages/LawResultPage.tsx
import { useLocation, useNavigate } from "react-router-dom";
import "./LawResultPage.css";

export default function LawResultPage() {
  const location = useLocation();
  const navigate = useNavigate();

  const results = location.state?.results;

  if (!results || results.length === 0) {
    return (
      <div className="lr-wrapper">
        <p className="lr-empty">법령 정보를 찾을 수 없습니다.</p>
      </div>
    );
  }

  return (
    <div className="lr-wrapper">
      <h2 className="lr-title">검색 결과</h2>

      <div className="lr-list">
        {results.map((item: any) => (
          <div
            key={item.id || item._id || item.lawId}
            className="lr-card"
            onClick={() => navigate(`/legal/${item.lawId}`)}  // ← 여기만 수정
          >
            <h3>{item.title}</h3>
            <p>{item.articleTitle || "조문 제목 없음"}</p>
          </div>
        ))}
      </div>
    </div>
  );
}
