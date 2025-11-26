// src/pages/LawDetail.tsx
import { useParams, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import axios from "axios";
import "./LawDetail.css";

export default function LawDetail() {
  const { lawId } = useParams();
  const navigate = useNavigate();

  const [law, setLaw] = useState<any>(null);
  const [loading, setLoading] = useState(true);
  const [selectedArticle, setSelectedArticle] = useState<any>(null);

  useEffect(() => {
    fetchLaw();
  }, [lawId]);

  const fetchLaw = async () => {
    try {
      const res = await axios.get(`http://localhost:8080/api/legal/${lawId}`);

      // ⚠️ 백엔드 필드를 프론트 기준으로 맞춰주는 변환
      const converted = {
        ...res.data,
        articles: res.data.articles.map((art: any) => ({
          articleNo: art.articleNumber,          // articleNumber → articleNo
          articleTitle: art.articleTitle || "",  // title 없으면 빈 문자열
          articleText: art.articleText || ""
        }))
      };

      setLaw(converted);

      if (converted.articles?.length > 0) {
        setSelectedArticle(converted.articles[0]);
      }
    } catch (err) {
      console.error("법령 상세 오류:", err);
      alert("법령 정보를 불러오는 중 오류가 발생했습니다.");
    } finally {
      setLoading(false);
    }
  };

  if (loading) return <div className="loading">불러오는 중...</div>;
  if (!law) return <div className="no-data">법령 정보를 찾을 수 없습니다.</div>;

  return (
    <div className="law-detail-wrapper">
      <button className="back-btn" onClick={() => navigate(-1)}>
        ← 뒤로가기
      </button>

      <h2 className="law-title">{law.title}</h2>

      <div className="law-layout">
        {/* 좌측 조문 목록 */}
        <div className="law-article-list">
          {law.articles?.map((art: any, idx: number) => (
            <div
              key={idx}
              className={`article-item ${
                selectedArticle?.articleNo === art.articleNo ? "active" : ""
              }`}
              onClick={() => setSelectedArticle(art)}
            >
              {art.articleNo} {art.articleTitle}
            </div>
          ))}
        </div>

        {/* 우측 조문 내용 */}
        <div className="law-article-content">
          <h3>
            {selectedArticle?.articleNo} {selectedArticle?.articleTitle}
          </h3>

          <div className="content-box">
            {selectedArticle?.articleText
              ?.split("\n")
              .map((line: string, idx: number) => (
                <p key={idx} className="article-line">
                  {line}
                </p>
              ))}
          </div>
        </div>
      </div>
    </div>
  );
}
