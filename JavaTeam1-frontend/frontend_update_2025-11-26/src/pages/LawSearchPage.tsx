// src/pages/LawSearchPage.tsx
import { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import "./LawSearchPage.css";

export default function LawSearchPage() {
  const [keyword, setKeyword] = useState("");
  const navigate = useNavigate();

  const handleSearch = async () => {
    if (!keyword.trim()) return;

    try {
      const res = await axios.get(
        `http://localhost:8080/api/legal/search?keyword=${encodeURIComponent(
          keyword
        )}`
      );

      navigate("/laws/result", { state: { results: res.data } });
    } catch (error) {
      console.error("법령 검색 오류:", error);
      alert("검색 중 오류가 발생했습니다.");
    }
  };

  return (
    <div className="l-wrapper">
      <h2 className="l-title">법령 검색</h2>
      <p className="l-desc">법령명, 조문 번호, 내용으로 검색할 수 있습니다.</p>

      <div className="l-search-box">
        <input
          type="text"
          placeholder="검색어를 입력하세요. 예) 임대차보호법, 최저임금법 등"
          value={keyword}
          onChange={(e) => setKeyword(e.target.value)}
        />

        <button onClick={handleSearch}>검색</button>
      </div>

      {/* 이미지 3개 */}
      <div className="l-fixed-image-wrapper">
        <img src="/images/임대차보호법.jpg" alt="img1" />
        <img src="/images/최저임금법.jpg" alt="img2" />
        <img src="/images/전세사기특별법.jpg" alt="img3" />
      </div>

      {/* 안내 문구 */}
      <p className="l-disclaimer">
        이 페이지의 내용은 법률 정보에 대한 이해를 돕기 위한 참고 자료입니다. 
        보다 정확하고 구체적인 법률적 도움이 필요하신 경우, 
        전문가와의 상담을 통해 해결하시는 것을 권장합니다.
      </p>
    </div>
  );
}
