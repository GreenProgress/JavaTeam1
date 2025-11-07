// API 모듈
// 백엔드와 통신하기 위한 함수들을 정의합니다.
// 서버의 context-path는 application.yml에서 /api 로 설정되어 있으므로
// 모든 엔드포인트는 BASE_URL 이후에 정의된 경로로 요청됩니다.

const BASE_URL = "http://localhost:8080/api";

// 활성화된 생활법률 상황 목록을 가져옵니다.
export async function fetchSituations() {
  const res = await fetch(`${BASE_URL}/Situation`);
  if (!res.ok) throw new Error("failed to fetch situations");
  return res.json();
}

// 특정 상황의 상세 정보를 가져옵니다.
export async function fetchSituationDetail(id) {
  const res = await fetch(`${BASE_URL}/Situation/${id}`);
  if (!res.ok) throw new Error("failed to fetch situation detail");
  return res.json();
}

// 특정 상황에 대한 질문 목록을 가져옵니다.
export async function fetchQuestions(id) {
  const res = await fetch(`${BASE_URL}/Situation/${id}/Question`);
  if (!res.ok) throw new Error("failed to fetch questions");
  return res.json();
}

// 법령 키워드 검색을 수행합니다. 백엔드에서 제목과 내용 기준으로 검색합니다.
export async function searchLegalDocuments(keyword, page = 0, size = 20) {
  const url = `${BASE_URL}/LegalDocument/search?keyword=${encodeURIComponent(
    keyword
  )}&page=${page}&size=${size}`;
  const res = await fetch(url);
  if (!res.ok) throw new Error("failed to search legal documents");
  return res.json();
}

// 전체 법령 목록을 가져옵니다. content 제외
export async function fetchAllLegalDocuments() {
  const res = await fetch(`${BASE_URL}/LegalDocument`);
  if (!res.ok) throw new Error("failed to fetch legal documents");
  return res.json();
}

// 특정 법령 코드(lawId)에 해당하는 모든 문서(조항)를 가져옵니다.
export async function fetchLegalDocumentsByLawId(lawId) {
  const res = await fetch(`${BASE_URL}/LegalDocument/${lawId}`);
  if (!res.ok) throw new Error("failed to fetch legal document by lawId");
  return res.json();
}

// 카테고리별 법령 문서를 가져옵니다. 옵션으로 페이지와 사이즈를 넘길 수 있습니다.
export async function fetchDocumentsByCategory(category, page = 0, size = 20) {
  const url = `${BASE_URL}/LegalDocument/categori/${encodeURIComponent(
    category
  )}?page=${page}&size=${size}`;
  const res = await fetch(url);
  if (!res.ok) throw new Error("failed to fetch documents by category");
  return res.json();
}