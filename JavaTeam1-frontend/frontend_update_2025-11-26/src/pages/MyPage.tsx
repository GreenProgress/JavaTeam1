// src/pages/MyPage.tsx

import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { getViewedSituations, getViewedLaws } from "../utils/history";
import "./MyPage.css";

export default function MyPage() {
  const navigate = useNavigate();

  const [recentSituations, setRecentSituations] = useState<any[]>([]);
  const [recentLaws, setRecentLaws] = useState<any[]>([]);

  useEffect(() => {
    setRecentSituations(getViewedSituations());
    setRecentLaws(getViewedLaws());
  }, []);

  // 로그아웃 기능
  const handleLogout = () => {
    localStorage.removeItem("loginUser");  // 로그인 정보 삭제
    localStorage.removeItem("token");      // 토큰 삭제

    alert("로그아웃 되었습니다.");
    navigate("/"); // 홈으로 이동
  };

  return (
    <div className="mypage-wrapper">
      <div className="mypage-card">
        <h2 className="mypage-title">마이페이지</h2>
        <p className="mypage-desc">최근 본 상황과 법령을 확인해보세요.</p>

        {/* 최근 본 상황 */}
        <section className="mypage-section">
          <h3>최근 본 상황</h3>

          {recentSituations.length === 0 ? (
            <p className="empty-text">최근 본 상황이 없습니다.</p>
          ) : (
            <ul className="mypage-list">
              {recentSituations.map((item) => (
                <li key={item.id} className="mypage-item">
                  {item.title}
                </li>
              ))}
            </ul>
          )}
        </section>

        {/* 최근 본 법령 */}
        <section className="mypage-section">
          <h3>최근 본 법령</h3>

          {recentLaws.length === 0 ? (
            <p className="empty-text">최근 본 법령이 없습니다.</p>
          ) : (
            <ul className="mypage-list">
              {recentLaws.map((item) => (
                <li key={item.lawId} className="mypage-item">
                  {item.title}
                </li>
              ))}
            </ul>
          )}
        </section>

        {/* 로그아웃 */}
        <button className="logout-btn" onClick={handleLogout}>
          로그아웃
        </button>
      </div>
    </div>
  );
}
