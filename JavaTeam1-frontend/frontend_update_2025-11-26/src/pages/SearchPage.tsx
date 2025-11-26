/* SearchPage.tsx (정확한 API 버전) */
import { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import "./SearchPage.css";

export default function SearchPage() {
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
      console.error("검색 오류:", error);
      alert("검색 중 오류가 발생했습니다.");
    }
  };

  return (
    <div className="s-wrapper">
      <h2 className="s-title">법령 검색</h2>

      <div className="s-input-group">
        <input
          type="text"
          placeholder="검색어를 입력하세요"
          value={keyword}
          onChange={(e) => setKeyword(e.target.value)}
        />
        <button onClick={handleSearch}>검색</button>
      </div>
    </div>
  );
}
