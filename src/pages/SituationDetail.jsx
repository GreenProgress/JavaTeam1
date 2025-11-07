import React, { useState, useEffect } from "react";
import { useParams, Link } from "react-router-dom";
import { fetchSituationDetail, fetchQuestions } from "../api/api";

/**
 * SituationDetail 컴포넌트
 *
 * 상황의 상세 정보와 관련 질문을 보여줍니다.
 */
export default function SituationDetail() {
  const { id } = useParams();
  const [situation, setSituation] = useState(null);
  const [questions, setQuestions] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const loadData = async () => {
      setLoading(true);
      setError(null);
      try {
        const detail = await fetchSituationDetail(id);
        setSituation(detail);
        const q = await fetchQuestions(id);
        setQuestions(q);
      } catch (err) {
        console.error(err);
        setError("세부 정보를 불러오는 데 실패했습니다.");
      } finally {
        setLoading(false);
      }
    };
    loadData();
  }, [id]);

  if (loading) {
    return <p style={{ padding: "20px" }}>불러오는 중...</p>;
  }
  if (error) {
    return (
      <p style={{ padding: "20px", color: "red" }}>{error}</p>
    );
  }
  if (!situation) {
    return <p style={{ padding: "20px" }}>상황을 찾을 수 없습니다.</p>;
  }

  return (
    <main className="detail-page" style={{ padding: "20px" }}>
      <h2>{situation.title}</h2>
      {situation.description && <p>{situation.description}</p>}

      {questions.length > 0 && (
        <section style={{ marginTop: "1.5rem" }}>
          <h3>질문</h3>
          <ul>
            {questions.map((q) => (
              <li key={q.id} style={{ marginBottom: "1rem" }}>
                <strong>{q.questionText}</strong>
                {/* questionType 이 choice일 경우 선택지를 보여줌 */}
                {q.questionType === "choice" && q.choices && (
                  <ul style={{ marginTop: "0.5rem", paddingLeft: "1.5rem" }}>
                    {q.choices.map((choice, idx) => (
                      <li key={idx}>{choice}</li>
                    ))}
                  </ul>
                )}
              </li>
            ))}
          </ul>
        </section>
      )}
      <Link to="/list" style={{ display: "inline-block", marginTop: "1.5rem" }}>
        목록으로 돌아가기
      </Link>
    </main>
  );
}