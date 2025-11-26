// src/pages/Home.tsx
import { useNavigate } from "react-router-dom";
import "./Home.css";

export default function Home() {
  const navigate = useNavigate();

  // 로그인 여부 판단 (토큰 존재 여부)
  const token = localStorage.getItem("token");

  return (
    <div className="home-container">

      {/* 상단 타이틀 */}
      <div className="home-title-box">
        <h1 className="home-title">생활 법률·권리 안내</h1>
      </div>

      {/* 카드 목록 */}
      <div className="home-card-list">

        {/* 상황 검색 → 상황검색 메인 화면으로 이동 */}
        <div
          className="home-card"
          onClick={() => navigate("/situation-search")}
        >
          <h2>상황 검색</h2>
          <p>일상 속 문제 상황을 검색하고 해결 방법을 확인</p>
        </div>

        {/* 법령 검색 */}
        <div
          className="home-card"
          onClick={() => navigate("/laws")}
        >
          <h2>법령 검색</h2>
          <p>국가법령DB 기반 법률·조항 전체 검색</p>
        </div>

        {/* 로그인 상태에 따라 버튼 변경 */}
        {!token ? (
          <>
            <div
              className="home-card"
              onClick={() => navigate("/login")}
            >
              <h2>로그인</h2>
              <p>로그인하여 개인화된 기능을 이용</p>
            </div>

            <div
              className="home-card"
              onClick={() => navigate("/signup")}
            >
              <h2>회원가입</h2>
              <p>새 계정을 만들고 서비스 이용</p>
            </div>
          </>
        ) : (
          <div
            className="home-card"
            onClick={() => navigate("/mypage")}
          >
            <h2>마이페이지</h2>
            <p>최근 본 법령·상황, 즐겨찾기 관리</p>
          </div>
        )}

      </div>

      {/* 아래 이미지 */}
      <div className="home-bottom-image-box">
        <img
          src="/images/대학생고민.jpg"
          alt="대학생 고민"
          className="home-bottom-image"
        />
      </div>

      
{/* 법률 안내 문구 */}
<p className="home-disclaimer">
  이 페이지의 내용은 법률 정보에 대한 이해를 돕기 위한 참고 자료입니다. 
  보다 정확하고 구체적인 법률적 도움이 필요하신 경우, 
  전문가와의 상담을 통해 해결하시는 것을 권장합니다.
</p>

    </div>
  );
}
