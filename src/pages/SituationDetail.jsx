import React, { useState, useEffect, useMemo } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
// api.js에서 필요한 모든 함수 임포트
import { fetchQuestions, submitResponse, requestAnalysis } from '../api/api';
// sessionId 생성을 위해 uuid 패키지 설치 필요
// 터미널에서: npm install uuid
import { v4 as uuidv4 } from 'uuid'; 
import "./SituationDetail.css"; // 간단한 스타일 추가 (파일 생성 필요)

/**
 * SituationDetail 컴포넌트
 *
 * 상황 상세 정보가 아닌, 상황별 '질문 페이지' 역할을 합니다.
 * 질문을 순서대로 보여주고, 답변을 수집하여 sessionId와 함께 서버로 전송합니다.
 * 모든 답변이 완료되면 분석을 요청하고 결과 페이지로 이동합니다.
 */
export default function SituationDetail() {
  const { id: situationId } = useParams(); // URL 파라미터에서 situationId 가져오기
  const navigate = useNavigate();

  const [questions, setQuestions] = useState([]);
  const [currentQuestionIndex, setCurrentQuestionIndex] = useState(0);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  
  // sessionId 관리 (localStorage 활용)
  // 사용자가 페이지에 들어올 때마다 고유한 세션 ID를 생성하거나 가져옵니다.
  const sessionId = useMemo(() => {
    let sid = localStorage.getItem('appSessionId');
    if (!sid) {
      sid = `sess_${uuidv4()}`;
      localStorage.setItem('appSessionId', sid);
    }
    return sid;
  }, []);

  // 1. 질문 목록 불러오기 (기존 로직 활용)
  useEffect(() => {
    const loadData = async () => {
      setLoading(true);
      setError(null);
      try {
        // fetchSituationDetail은 더 이상 필요 없음 (필요시 로드)
        // const detail = await fetchSituationDetail(situationId);
        // setSituation(detail);
        
        const q = await fetchQuestions(situationId);
        if (q.length === 0) {
          setError("이 상황에 대한 질문이 없습니다.");
        }
        setQuestions(q);
      } catch (err) {
        console.error(err);
        setError("질문을 불러오는 데 실패했습니다.");
      } finally {
        setLoading(false);
      }
    };
    loadData();
  }, [situationId]);

  // 2. 답변 처리 함수
  const handleAnswer = async (responseValue) => {
    const currentQuestion = questions[currentQuestionIndex];
    
    // 백엔드 ResponseController로 보낼 데이터 준비
    const responsePayload = {
      sessionId: sessionId,
      situationId: situationId,
      questionId: currentQuestion.id, // Question의 실제 ID (_id)
      responseValue: responseValue,  // "YES" 또는 "NO"
      orderIndex: currentQuestion.orderIndex
    };
    
    try {
      // API로 답변 전송
      await submitResponse(responsePayload);

      // 다음 질문으로 이동
      const nextIndex = currentQuestionIndex + 1;
      if (nextIndex < questions.length) {
        setCurrentQuestionIndex(nextIndex);
      } else {
        // 모든 질문 완료
        handleAnalysis();
      }
    } catch (err) {
      console.error(err);
      alert("답변 제출 중 오류가 발생했습니다.");
    }
  };

  // 3. 모든 질문 완료 시 분석 요청
  const handleAnalysis = async () => {
    setLoading(true); // "분석 중..."
    try {
      // 백엔드 AnalysisController로 분석 요청
      const analysisPayload = { sessionId, situationId };
      const result = await requestAnalysis(analysisPayload);

      // situationTitle도 함께 전달
      const situationTitle = questions.length > 0 ? questions[0].situationTitle : "분석 결과";

      // 결과 페이지로 이동 (state로 결과 객체와 상황 제목 전달)
      navigate('/result', { state: { result: result, situationTitle: situationTitle } });
    } catch (err) {
      console.error(err);
      alert('분석 요청 중 오류가 발생했습니다.');
      setLoading(false);
    }
  };

  // 4. 렌더링
  if (loading && questions.length === 0) {
    return <p style={{ padding: "20px" }}>질문을 불러오는 중...</p>;
  }
  
  if (error) {
    return (
      <p style={{ padding: "20px", color: "red" }}>{error}</p>
    );
  }
  
  // 모든 질문에 답을 완료하고 분석 중일 때
  if (loading) {
     return <p style={{ padding: "20px" }}>분석 중입니다...</p>;
  }

  if (questions.length === 0) {
     return <p style={{ padding: "20px" }}>질문을 찾을 수 없습니다.</p>;
  }

  const currentQuestion = questions[currentQuestionIndex];

  return (
    <main className="detail-page" style={{ padding: "20px" }}>
      <h2>{currentQuestion.situationTitle}</h2>
      
      <div className="question-container">
        <h3>Q{currentQuestion.orderIndex}. {currentQuestion.questionText}</h3>
        {currentQuestion.helpText && <p className="help-text">ℹ️ {currentQuestion.helpText}</p>}
        
        <div className="answer-buttons">
          <button onClick={() => handleAnswer("YES")}>예</button>
          <button onClick={() => handleAnswer("NO")}>아니오</button>
        </div>
        
        <p className="progress-text">
          {currentQuestionIndex + 1} / {questions.length}
        </p>
      </div>
    </main>
  );
}