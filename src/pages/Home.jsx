import React, { useState } from "react";
import { Link } from "react-router-dom";
import { searchLegalDocuments } from "../api/api";
import "./Home.css";

/**
 * Home 컴포넌트
 *
 * 메인 페이지로, 검색창을 통해 법령 키워드 검색이 가능합니다.
 * 검색 결과는 법령 제목을 리스트 형태로 보여주며, 각 항목을 클릭하면
 * 해당 법령의 상세 페이지로 이동합니다.
 */
export default function Home() {
  const [query, setQuery] = useState("");
  const [results, setResults] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const handleSearch = async () => {
    if (!query.trim()) return;
    setLoading(true);
    setError(null);
    try {
      const data = await searchLegalDocuments(query);
      // search API에서 페이지 객체를 반환할 수도 있으므로 content를 우선 사용합니다.
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

  return (
    <main className="home-main">
      <section className="hero-section">
        <h1>생활 법률·권리 안내 도우미</h1>
        <p className="tagline">당신의 권리를 쉽게 찾고 이해하세요.</p>
        <div className="search-wrapper">
          <input
            type="text"
            placeholder="법령 키워드 또는 상황을 검색하세요..."
            aria-label="법령 검색"
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

      {results.length > 0 && (
        <section className="category-section">
          <h2>검색 결과 (법령)</h2>
          <ul className="category-list">
            {results.map((doc, idx) => (
              <li key={doc.id || idx}>
                <Link to={`/law/${doc.lawId}`}>{doc.title || doc.articleTitle || doc.lawId}</Link>
              </li>
            ))}
          </ul>
        </section>
      )}

      {!loading && results.length === 0 && query.trim() && !error && (
        <section className="category-section">
          <p>검색 결과가 없습니다.</p>
        </section>
      )}
    </main>
  );
}