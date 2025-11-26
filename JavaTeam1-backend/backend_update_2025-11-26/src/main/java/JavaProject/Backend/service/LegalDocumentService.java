package JavaProject.Backend.service;

import JavaProject.Backend.domain.LegalDocument;
import JavaProject.Backend.repository.LegalDocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class LegalDocumentService {

    private final LegalDocumentRepository repository;

    /**
     * 검색: 제목 / 조항제목 / 본문
     * (정부 법령 구조 그대로 사용)
     */
    public List<LegalDocument> searchByKeyword(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return Collections.emptyList();
        }

        String trimmed = keyword.trim();

        Set<LegalDocument> results = new LinkedHashSet<>();

        results.addAll(repository.findByTitleContainingIgnoreCase(trimmed));
        results.addAll(repository.findByArticleTitleContainingIgnoreCase(trimmed));
        results.addAll(repository.findByArticleTextContainingIgnoreCase(trimmed));

        return new ArrayList<>(results);
    }

    /**
     * lawId 로 LegalDocument 여러 개를 조회해서
     * 하나의 "법령 + 조문 목록" 구조로 병합해서 반환.
     *
     * 정부 법령 구조(조문 1개 = 문서 1개)만 사용.
     *
     * 프론트에 내려주는 형태:
     * {
     *   "lawId": "000129",
     *   "title": "최저임금법",
     *   "articles": [
     *      {"articleNo": "제1조", "articleTitle": "...", "articleText": "..."},
     *      ...
     *   ]
     * }
     */
    public Map<String, Object> getMergedLawByLawId(String lawId) {
        if (lawId == null || lawId.trim().isEmpty()) {
            return null;
        }

        List<LegalDocument> docs = repository.findByLawId(lawId);

        if (docs == null || docs.isEmpty()) {
            return null;
        }

        // 첫 문서를 기준으로 제목 사용
        LegalDocument first = docs.get(0);

        // 조문 리스트 생성
        List<Map<String, String>> articles = new ArrayList<>();

        for (LegalDocument d : docs) {
            Map<String, String> article = new LinkedHashMap<>();
            article.put("articleNo", Optional.ofNullable(d.getArticleNo()).orElse(""));
            article.put("articleTitle", Optional.ofNullable(d.getArticleTitle()).orElse(""));
            article.put("articleText", Optional.ofNullable(d.getArticleText()).orElse(""));
            articles.add(article);
        }

        // 조문 번호 기준 정렬
        articles.sort(Comparator.comparing(a -> a.getOrDefault("articleNo", "")));

        // 최종 결과 구조
        Map<String, Object> merged = new LinkedHashMap<>();
        merged.put("lawId", lawId);
        merged.put("title", first.getTitle());
        merged.put("articles", articles);

        return merged;
    }

    /**
     * ID로 개별 문서 조회
     */
    public LegalDocument getById(String id) {
        if (id == null || id.trim().isEmpty()) return null;
        return repository.findById(id).orElse(null);
    }
}
