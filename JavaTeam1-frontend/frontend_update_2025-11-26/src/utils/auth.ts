// 로그인 여부 체크
export function isLoggedIn() {
  return !!localStorage.getItem("userToken");
}

// 로그인 처리
export function login(token: string) {
  localStorage.setItem("userToken", token);
}

// 로그아웃 처리
export function logout() {
  localStorage.removeItem("userToken");
}
