import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { fetchSituations } from "../api/api";

/**
 * SituationList 컴포넌트
 *
 * 백엔드에서 생활 법률 상황 목록을 가져와 리스트로 표시합니다.
 */
export default function SituationList() {
  const [situations, setSituations] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    const load = async () => {
      setLoading(true);
      setError(null);
      try {
        const data = await fetchSituations();
        setSituations(data);
      } catch (err) {
        console.error(err);
        setError("목록을 불러오는 데 실패했습니다.");
      } finally {
        setLoading(false);
      }
    };
    load();
  }, []);

  if (loading) {
    return <p style={{ padding: "20px" }}>불러오는 중...</p>;
  }
  if (error) {
    return (
      <p style={{ padding: "20px", color: "red" }}>{error}</p>
    );
  }

  return (
    <main className="list-page" style={{ padding: "20px" }}>
      <h2>생활법률 상황 목록</h2>
      <ul>
        {situations.map((item) => (
          <li key={item.id}>
            <Link to={`/detail/${item.id}`}>{item.title}</Link>
          </li>
        ))}
      </ul>
    </main>
  );
}