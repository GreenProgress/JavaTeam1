import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Home from "../pages/Home.jsx";
import SituationList from "../pages/SituationList.jsx";
import SituationDetail from "../pages/SituationDetail.jsx";
import LawDetail from "../pages/LawDetail.jsx";
import AnalysisResultPage from "../pages/AnalysisResultPage.jsx"; 

/**
 * AppRouter
 *
 * 라우트 정의를 담당하는 컴포넌트입니다. 루트 경로(`/`)에는 홈 페이지를,
 * `/list` 경로에는 상황 목록을, `/detail/:id` 경로에는 상황 상세(질문 페이지),
 * `/law/:lawId` 경로에는 법령 상세 페이지를 연결합니다.
 */
export default function AppRouter() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/list" element={<SituationList />} />
        <Route path="/detail/:id" element={<SituationDetail />} /> {/* 질문 페이지 */}
        <Route path="/law/:lawId" element={<LawDetail />} />
        <Route path="/result" element={<AnalysisResultPage />} />
      </Routes>
    </Router>
  );
}