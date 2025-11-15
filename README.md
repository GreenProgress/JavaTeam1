# 
 
## 핵심 기능 변경점


* **기존 버전 (단순 검색)**
    1.  `Home.jsx`: "법령 키워드"를 검색합니다.
    2.  `LawDetail.jsx`: 검색 결과를 클릭하면 법령 상세 내용을 봅니다.

* **신규 버전 (상호작용 및 분석)**
    1.  `Home.jsx`: "상황 키워드" (예: '임금 체불')를 검색합니다.
    2.  `SituationDetail.jsx`: 검색 결과를 클릭하면, "상호작용형 질문 페이지"로 이동하여 '예/아니오'로 답변을 진행합니다.
    3.  `api.js`: 답변이 완료되면 서버에 '분석 요청'을 보냅니다.
    4.  `AnalysisResultPage.jsx`: 서버에서 받은 맞춤형 분석 결과를 새 페이지에서 확인합니다.
    5.  `LawDetail.jsx`: 결과 페이지에서 '관련 법령' 버튼을 눌러 법적 근거를 확인합니다.

---

## 파일별 주요 변경점

### 1. `pages/Home.jsx` (검색 대상 변경)

* **기존**: `searchLegalDocuments` (법령 검색) API를 호출했습니다.
* **신규**: `searchSituations` (상황 검색) API를 호출하도록 변경되었습니다. 또한, 검색 결과를 `SituationCard` 컴포넌트로 보여주고, 클릭 시 `/detail/:id` (질문 페이지)로 이동시킵니다.

### 2. `pages/SituationDetail.jsx` (완전한 재작성)

* **기존**: 상황 정보와 질문 목록을 **단순히 보여주기만** 하는 정보 페이지였습니다.
* **신규**: **핵심 기능인 '상호작용형 질문지'**로 완전히 재작성되었습니다.
    * 질문을 한 번에 하나씩 보여줍니다.
    * '예/아니오' 버튼 클릭 시 `submitResponse` API를 호출하여 답변을 서버에 저장합니다.
    * 모든 질문이 끝나면 `requestAnalysis` API를 호출하여 분석을 요청합니다.
    * 분석 결과를 `state`에 담아 `/result` 페이지로 이동시킵니다.

### 3. `api/api.js` (API 함수 대거 추가)

* **기존**: 7개의 API 함수가 있었습니다.
* **신규**: '신규 버전'의 핵심 기능을 지원하기 위해 4개의 API 함수가 **추가**되었습니다.
    * `searchSituations`: 상황(Situation) 검색
    * `submitResponse`: 사용자 답변 제출
    * `requestAnalysis`: 최종 분석 요청
    * `fetchLegalArticles`: 법령 조항 원문 조회 (결과 페이지용)

### 4. `router/Router.jsx` (라우트 추가)

* **기존**: 4개의 경로가 있었습니다.
* **신규**: 분석 결과 페이지를 위한 `/result` 경로가 **추가**되었습니다.

### 5. `components/SituationCard.jsx` (컴포넌트 수정)

* **기존**: `title`, `description` 등 개별 데이터를 props로 받았습니다.
* **신규**: `situation` 객체와 `onClick` 이벤트를 props로 받도록 수정되어, `Home.jsx`의 검색 결과에서 재사용 가능하고 클릭 가능한 카드로 변경되었습니다.

---

## 새로 추가된 파일

* **`pages/AnalysisResultPage.jsx`**: 서버로부터 받은 최종 분석 결과를 표시하는 '결과 페이지'입니다.
* **`pages/AnalysisResultPage.css`**: '결과 페이지'의 전용 스타일시트입니다.
* **`pages/SituationDetail.css`**: '질문 페이지'의 전용 스타일시트입니다.

---

## 변경 사항이 거의 없는 파일

아래 파일들은 '기존'과 '신규' 버전 간에 내용이 동일합니다.

* `App.jsx`
* `main.jsx`
* `index.css`
* `pages/Home.css`
* `pages/LawDetail.jsx`
* `pages/SituationList.jsx`
* `assets/react.svg` (두 파일 모두 비어있음)





# React + TypeScript + Vite

This template provides a minimal setup to get React working in Vite with HMR and some ESLint rules.

Currently, two official plugins are available:

- [@vitejs/plugin-react](https://github.com/vitejs/vite-plugin-react/blob/main/packages/plugin-react) uses [Babel](https://babeljs.io/) (or [oxc](https://oxc.rs) when used in [rolldown-vite](https://vite.dev/guide/rolldown)) for Fast Refresh
- [@vitejs/plugin-react-swc](https://github.com/vitejs/vite-plugin-react/blob/main/packages/plugin-react-swc) uses [SWC](https://swc.rs/) for Fast Refresh

## React Compiler

The React Compiler is not enabled on this template because of its impact on dev & build performances. To add it, see [this documentation](https://react.dev/learn/react-compiler/installation).

## Expanding the ESLint configuration

If you are developing a production application, we recommend updating the configuration to enable type-aware lint rules:

```js
export default defineConfig([
  globalIgnores(['dist']),
  {
    files: ['**/*.{ts,tsx}'],
    extends: [
      // Other configs...

      // Remove tseslint.configs.recommended and replace with this
      tseslint.configs.recommendedTypeChecked,
      // Alternatively, use this for stricter rules
      tseslint.configs.strictTypeChecked,
      // Optionally, add this for stylistic rules
      tseslint.configs.stylisticTypeChecked,

      // Other configs...
    ],
    languageOptions: {
      parserOptions: {
        project: ['./tsconfig.node.json', './tsconfig.app.json'],
        tsconfigRootDir: import.meta.dirname,
      },
      // other options...
    },
  },
])
```

You can also install [eslint-plugin-react-x](https://github.com/Rel1cx/eslint-react/tree/main/packages/plugins/eslint-plugin-react-x) and [eslint-plugin-react-dom](https://github.com/Rel1cx/eslint-react/tree/main/packages/plugins/eslint-plugin-react-dom) for React-specific lint rules:

```js
// eslint.config.js
import reactX from 'eslint-plugin-react-x'
import reactDom from 'eslint-plugin-react-dom'

export default defineConfig([
  globalIgnores(['dist']),
  {
    files: ['**/*.{ts,tsx}'],
    extends: [
      // Other configs...
      // Enable lint rules for React
      reactX.configs['recommended-typescript'],
      // Enable lint rules for React DOM
      reactDom.configs.recommended,
    ],
    languageOptions: {
      parserOptions: {
        project: ['./tsconfig.node.json', './tsconfig.app.json'],
        tsconfigRootDir: import.meta.dirname,
      },
      // other options...
    },
  },
])
```
