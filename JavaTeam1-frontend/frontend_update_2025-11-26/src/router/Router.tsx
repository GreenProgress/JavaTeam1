// src/router/Router.tsx
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";

/* 페이지 임포트 */
import Home from "../pages/Home";
import SearchPage from "../pages/SearchPage";

import SituationSearch from "../pages/SituationSearch";
import SituationList from "../pages/SituationList";
import SituationDetailPage from "../pages/SituationDetailPage";

import LawSearchPage from "../pages/LawSearchPage";
import LawResultPage from "../pages/LawResultPage";
import LawDetail from "../pages/LawDetail";

import Login from "../pages/Login";
import Signup from "../pages/Signup";
import MyPage from "../pages/MyPage";

export default function Router() {
  return (
    <BrowserRouter>
      <Routes>
        {/* 기본 진입 → /home 으로 이동 */}
        <Route path="/" element={<Navigate to="/home" replace />} />

        {/* 홈 화면 */}
        <Route path="/home" element={<Home />} />

        {/* 검색 페이지(기존 SearchPage 유지) */}
        <Route path="/search" element={<SearchPage />} />

        {/* 상황 검색 메인 */}
        <Route path="/situation-search" element={<SituationSearch />} />

        {/* 상황 목록 */}
        <Route path="/situations" element={<SituationList />} />

        {/* 상황 상세 */}
        <Route
          path="/situations/:situationId"
          element={<SituationDetailPage />}
        />

        {/* 법령 검색 메인 */}
        <Route path="/laws" element={<LawSearchPage />} />

        {/* 법령 검색 결과 */}
        <Route path="/laws/result" element={<LawResultPage />} />

        {/* 법령 상세 – 여기만 수정됨 (/legal/:lawId) */}
        <Route path="/legal/:lawId" element={<LawDetail />} />

        {/* 로그인 / 회원가입 / 마이페이지 */}
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<Signup />} />
        <Route path="/mypage" element={<MyPage />} />

        {/* 없는 주소 → /home 으로 돌리기 */}
        <Route path="*" element={<Navigate to="/home" replace />} />
      </Routes>
    </BrowserRouter>
  );
}
