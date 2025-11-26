// src/pages/SituationList.tsx
import { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import axios from "axios";
import "./SituationList.css";

export default function SituationList() {
  const location = useLocation();
  const navigate = useNavigate();

  const [situations, setSituations] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    // SearchPage에서 검색 결과 전달된 경우
    if (location.state && location.state.results) {
      setSituations(location.state.results);
      setLoading(false);
    } else {
      // 직접 URL로 들어온 경우 전체 목록 가져오기
      fetchAllSituations();
    }
  }, [location.state]);

  // 전체 조회
  const fetchAllSituations = async () => {
    try {
      const res = await axios.get("http://localhost:8080/api/situations");
      setSituations(res.data);
    } catch (error) {
      console.error("상황 목록 로딩 오류:", error);
      alert("상황 목록 불러오기 중 오류 발생");
    } finally {
      setLoading(false);
    }
  };

  const goDetail = (id: string) => {
    navigate(`/situations/${id}`);
  };

  if (loading) return <div className="loading">불러오는 중...</div>;

  return (
    <div className="situation-list-container">
      <h2 className="list-title">검색 결과</h2>

      {situations.length === 0 ? (
        <p className="no-data">검색 결과가 없습니다.</p>
      ) : (
        <div className="situation-list">
          {situations.map((item, index) => {
            const desc =
              item.summary ||
              item.description ||
              item.detail ||
              item.explain ||
              "설명 없음";

            return (
              <div
                key={item._id || item.id || index}  // key 안정 처리
                className="situation-card"
                onClick={() => goDetail(item._id || item.id)}  // _id 또는 id 사용
              >
                <p className="situation-title">{item.title}</p>
                <p className="situation-desc">{desc}</p>
              </div>
            );
          })}
        </div>
      )}
    </div>
  );
}
