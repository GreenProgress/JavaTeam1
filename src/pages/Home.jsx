import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { searchSituations } from "../api/api";
import SituationCard from '../components/SituationCard';
import "./Home.css";

/**
 * Home 컴포넌트
 *
 * 검색창을 통해 '상황'을 검색합니다.
 * 검색 결과는 SituationCard 리스트로 보여주며, 각 항목을 클릭하면
 * 해당 상황의 '질문 페이지'(/detail/:id)로 이동합니다.
 */
export default function Home() {
  const [query, setQuery] = useState("");
  const [results, setResults] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const navigate = useNavigate(); // 페이지 이동 훅

  const handleSearch = async () => {
    if (!query.trim()) return;
    setLoading(true);
    setError(null);
    try {
      const data = await searchSituations(query);
      // API가 페이징 객체를 반환할 수도, 리스트를 바로 반환할 수도 있음
      const items = data.content || data; 
      setResults(items);
    } catch (err) {
      console.error(err);
      setError("검색 중 오류가 발생했습니다.");
      setResults([]);
    } finally {
      setLoading(false);
    }
  };

  // 검색된 상황 클릭 시 질문 페이지로 이동하는 함수
  const handleSituationClick = (situationId) => {
    // Router.jsx에 정의된 /detail/:id 경로로 이동
    navigate(`/detail/${situationId}`);
  };

  return (
    <main className="home-main">
      <section className="hero-section">
        <h1>생활 법률·권리 안내 도우미</h1>
        <p className="tagline">당신의 권리를 쉽게 찾고 이해하세요.</p>
        <div className="search-wrapper">
          <input
            type="text"
            placeholder="상황을 검색하세요..."
            aria-label="상황 검색"
            className="search-input"
            value={query}
            onChange={(e) => setQuery(e.target.value)}
            onKeyDown={(e) => {
              if (e.key === "Enter") {
                handleSearch();
              }
            }}
          />
          <button className="search-button" onClick={handleSearch}>
            검색
          </button>
        </div>
      </section>

      {loading && (
        <section className="category-section">
          <p>검색 중입니다...</p>
        </section>
      )}

      {error && (
        <section className="category-section">
          <p style={{ color: "red" }}>{error}</p>
        </section>
      )}

      {/* 검색 결과를 SituationCard로 렌더링 */}
      {results.length > 0 && (
        <section className="category-section">
          <h2>'<b>{query}</b>' 관련 상황</h2>
          <div className="situation-card-list">
            {results.map((situation) => (
              <SituationCard 
                key={situation.id} 
                situation={situation} //'situation' prop으로 객체 전체 전달
                onClick={() => handleSituationClick(situation.id)} // 클릭 이벤트
              />
            ))}
          </div>
        </section>
      )}

      {!loading && results.length === 0 && query.trim() && !error && (
        <section className="category-section">
          <p>'{query}'에 대한 검색 결과가 없습니다.</p>
        </section>
      )}
    </main>
  );
}