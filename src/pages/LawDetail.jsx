import React, { useState, useEffect } from "react";
import { useParams, Link } from "react-router-dom";
import { fetchLegalDocumentsByLawId } from "../api/api";

/**
 * LawDetail 컴포넌트
 *
 * 법령 코드(lawId)를 URL 파라미터로 받아 해당 법령에 포함된 조항들을 표시합니다.
 * 백엔드에서는 같은 lawId를 가진 여러 조항이 리스트로 반환됩니다.
 */
export default function LawDetail() {
  const { lawId } = useParams();
  const [documents, setDocuments] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const loadData = async () => {
      setLoading(true);
      setError(null);
      try {
        const data = await fetchLegalDocumentsByLawId(lawId);
        setDocuments(data);
      } catch (err) {
        console.error(err);
        setError("법령 정보를 불러오는 데 실패했습니다.");
      } finally {
        setLoading(false);
      }
    };
    loadData();
  }, [lawId]);

  if (loading) return <p>불러오는 중...</p>;
  if (error) return <p style={{ color: "red" }}>{error}</p>;

  return (
    <main className="law-detail-page" style={{ padding: "20px" }}>
      <h2>법령 상세: {lawId}</h2>
      {documents.length > 0 ? (
        <div>
          {documents.map((doc) => (
            <article key={doc.id} style={{ marginBottom: "1.5rem" }}>
              {/* 제목과 조항 번호 */}
              <h3>
                {doc.articleNo && <span>{doc.articleNo}조 </span>}
                {doc.articleTitle || doc.title || "제목 없음"}
              </h3>
              {/* 조항 본문 */}
              {doc.articleText && <p>{doc.articleText}</p>}
              {/* 출처 링크 */}
              {doc.sourceUrl && (
                <p>
                  <a
                    href={doc.sourceUrl}
                    target="_blank"
                    rel="noopener noreferrer"
                  >
                    출처 보기
                  </a>
                </p>
              )}
            </article>
          ))}
        </div>
      ) : (
        <p>해당 법령 데이터를 찾을 수 없습니다.</p>
      )}
      <Link to="/">메인으로 돌아가기</Link>
    </main>
  );
}