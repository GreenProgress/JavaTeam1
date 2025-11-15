import React, { useState, useEffect } from 'react';
import { useLocation, Link } from 'react-router-dom';
import { fetchLegalArticles } from '../api/api'; 
import "./AnalysisResultPage.css";

/**
 * AnalysisResultPage 컴포넌트
 *
 * 분석 결과를 표시합니다.
 */
const AnalysisResultPage = () => {
  const location = useLocation();
  const { result, situationTitle } = location.state || {}; // SituationDetail에서 넘겨받은 객체

  const [legalTexts, setLegalTexts] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    // 결과 객체가 없거나, 법령 정보가 없으면 로딩 종료
    if (!result || !result.relatedLaws || result.relatedLaws.length === 0) {
      setLoading(false);
      return;
    }

    const loadLegalTexts = async () => {
      setLoading(true);
      try {
        // API 호출 없이, 법령의 제목과 ID만 상태에 저장합니다.
        const allTexts = [];
        for (const law of result.relatedLaws) {
          allTexts.push({ title: law.title, lawId: law.lawId });
        }
        setLegalTexts(allTexts);
      } catch (error) {
        console.error(error);
      } finally {
        setLoading(false);
      }
    };

    loadLegalTexts();
  }, [result]);

  if (!result) {
    return (
      <div className="result-container">
        <h2>잘못된 접근입니다.</h2>
        <p>분석 결과가 없습니다. 홈에서 다시 시작해주세요.</p>
        <Link to="/">홈으로 돌아가기</Link>
      </div>
    );
  }

  return (
    <div className="result-container">
      <h2>{situationTitle || `분석 결과: ${result.situationId}`}</h2>
      
      <div className="result-section summary">
        <h3>결론</h3>
        <p>{result.resultSummary}</p>
      </div>

      <div className="result-section">
        <h3>다음 절차를 따르세요</h3>
        <ul className="procedure-list">
          {result.procedures.map((proc, index) => (
            <li key={index}>{proc}</li>
          ))}
        </ul>
      </div>

      <div className="result-section">
        <h3>필요한 서류들</h3>
        <ul className="checklist">
          {result.checklist.map((item, index) => (
            <li key={index}>✅ {item}</li>
          ))}
        </ul>
      </div>

      <div className="result-section">
        <h3>관련 법령 근거</h3>
        {loading ? (
          <p>법령 정보 로딩 중...</p>
        ) : (
          legalTexts.map((law, lawIndex) => (
            <div key={lawIndex} className="law-section">
              <h4>{law.title}</h4>

              {/* '원문 전체보기' 버튼을 렌더링합니다. */}
              {law.lawId && (
                <Link to={`/law/${law.lawId}`} className="law-link-button">
                  {law.title} 원문 전체보기
                </Link>
              )}
              
            </div>
          ))
        )}
      </div>

       <Link to="/">홈으로 돌아가기</Link>
    </div>
  );
};

export default AnalysisResultPage;