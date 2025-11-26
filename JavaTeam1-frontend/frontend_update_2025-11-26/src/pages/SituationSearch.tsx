// src/pages/SituationSearch.tsx
import { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import "./SituationSearch.css";

export default function SituationSearch() {
  const [keyword, setKeyword] = useState("");
  const navigate = useNavigate();

  const handleSearch = async () => {
    if (!keyword.trim()) return;

    try {
      const res = await axios.get(
        `http://localhost:8080/api/situations?keyword=${encodeURIComponent(
          keyword
        )}`
      );

      navigate("/situations", { state: { results: res.data } });
    } catch (error) {
      console.error("검색 오류:", error);
      alert("검색 중 오류가 발생했습니다.");
    }
  };

  return (
    <div className="s-wrapper">
      <h2 className="s-title">상황 검색</h2>
      <p className="s-desc">
        알바, 임대차, 임금체불 등 생활에서 자주 겪는 문제를 검색해 보세요.
      </p>

      {/* 검색창 */}
      <div className="s-search-box">
        <input
          type="text"
          placeholder="검색어를 입력하세요 예) 임금체불,전세사기,최저임금 등"
          value={keyword}
          onChange={(e) => setKeyword(e.target.value)}
          onKeyDown={(e) => e.key === "Enter" && handleSearch()}
        />
        <button onClick={handleSearch}>검색</button>
      </div>

      {/* 이미지 3개 */}
      <div className="s-fixed-image-wrapper">
        <img src="/images/대학생전세.jpg" alt="대학생전세" />
        <img src="/images/임금체불.jpg" alt="임금체불" />
        <img src="/images/최저임금(1).jpg" alt="최저임금" />
      </div>

      {/* 안내 문구 추가 */}
      <p className="s-disclaimer">
        이 페이지의 내용은 법률 정보에 대한 이해를 돕기 위한 참고 자료입니다. 
        보다 정확하고 구체적인 법률적 도움이 필요하신 경우, 
        전문가와의 상담을 통해 해결하시는 것을 권장합니다.
      </p>
    </div>
  );
}
