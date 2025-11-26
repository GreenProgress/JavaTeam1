// src/pages/SituationDetailPage.tsx

import { useParams, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import axios from "axios";
import "./SituationDetailPage.css";

export default function SituationDetailPage() {
  const { situationId } = useParams();
  const navigate = useNavigate();

  const [situation, setSituation] = useState<any>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchSituation = async () => {
      try {
        const res = await axios.get(
          `http://localhost:8080/api/situations/${situationId}`
        );

        setSituation(res.data);
        setLoading(false);
      } catch (error) {
        console.error("상세 조회 오류:", error);
        setLoading(false);
      }
    };

    fetchSituation();
  }, [situationId]);

  if (loading) return <div className="sd-container">불러오는 중...</div>;
  if (!situation)
    return (
      <div className="sd-container">
        데이터를 찾을 수 없습니다.
      </div>
    );

  return (
    <div className="sd-container">
      <button className="sd-back-btn" onClick={() => navigate(-1)}>
        ← 돌아가기
      </button>

      <h2 className="sd-title">{situation.title}</h2>
      <p className="sd-description">{situation.description}</p>

      <h3 className="sd-subtitle">관련 법령</h3>

      <div className="sd-law-list">
        {situation.relatedLaws && situation.relatedLaws.length > 0 ? (
          situation.relatedLaws.map((law: any, index: number) => {

            // ★★★ 핵심 추가: 조문ID(005243_3)를 → 순수 lawId(005243)로 정규화
            const pureLawId = law.lawId.includes("_")
              ? law.lawId.split("_")[0]
              : law.lawId;

            return (
              <div
                key={index}
                className="sd-law-item"
                onClick={() => navigate(`/legal/${pureLawId}`)}
              >
                <p className="sd-law-title">법령 코드: {law.lawId}</p>
              </div>
            );
          })
        ) : (
          <p className="sd-empty">관련 법령이 없습니다.</p>
        )}
      </div>
    </div>
  );
}
