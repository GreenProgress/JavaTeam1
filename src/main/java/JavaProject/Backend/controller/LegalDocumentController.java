package JavaProject.Backend.controller;

import JavaProject.Backend.domain.LegalDocument;
import JavaProject.Backend.service.LegalDocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map; // 1. Map 임포트
import java.util.LinkedHashMap; // 2. LinkedHashMap 임포트 (순서 유지를 위해)
import java.util.ArrayList; // 3. ArrayList 임포트

/**
 * 법령 문서 컨트롤러
 *
 * 기본 경로: /api/LegalDocument (서버 context-path=/api)
 *
 * 응답 규칙:
 * - size <= 0: 배열(List<LegalDocument>)로 반환
 * - size > 0 : PageResponse DTO로 안정된 스키마로 반환
 */
@RestController
@RequestMapping("/LegalDocument")
@RequiredArgsConstructor
public class LegalDocumentController {

    private final LegalDocumentService legalDocumentService;

    /**
     * 페이지 응답 DTO
     */
    public record PageResponse<T>(
            List<T> content,
            long totalElements,
            int totalPages,
            int number,
            int size
    ) {}

    /**
     * 법령 목록 조회 (content 제외)
     * GET /api/LegalDocument
     * 항상 리스트로 반환
     */
    @GetMapping
    public ResponseEntity<List<LegalDocument>> getAllDocuments() {
        List<LegalDocument> documents = legalDocumentService.getAllDocuments();
        return ResponseEntity.ok(documents);
    }

    /**
     * 특정 법령 상세 조회 (lawId에 속한 모든 조항)
     * GET /api/LegalDocument/{lawId}
     * 항상 리스트로 반환 (삽입 순서)
     */
    @GetMapping("/{lawId}")
    public ResponseEntity<List<LegalDocument>> getDocumentsByLawId(@PathVariable String lawId) {
        List<LegalDocument> docs = legalDocumentService.getDocumentsByLawId(lawId);
        return ResponseEntity.ok(docs);
    }

    /**
     * 카테고리별 법령 조회
     * GET /api/LegalDocument/categori/{categori}?page=0&size=20
     * - size <= 0: 리스트로 반환
     * - size > 0 : PageResponse로 반환
     */
    @GetMapping("/categori/{categori}")
    public ResponseEntity<?> getDocumentsByCategory(
            @PathVariable("categori") String categori,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        // 서비스에서 전체 리스트를 가져온 뒤 컨트롤러에서 수동 페이징
        List<LegalDocument> all = legalDocumentService.getDocumentsByCategori(categori);

        // --- [추가] lawId 기준으로 중복 제거 ---
        Map<String, LegalDocument> uniqueDocsMap = new LinkedHashMap<>();
        for (LegalDocument doc : all) {
            uniqueDocsMap.putIfAbsent(doc.getLawId(), doc);
        }
        List<LegalDocument> filteredList = new ArrayList<>(uniqueDocsMap.values());
        // --- 중복 제거 끝 ---


        if (size <= 0) {
            // return ResponseEntity.ok(all); // [수정 전]
            return ResponseEntity.ok(filteredList); // [수정 후]
        }

        // PageResponse<LegalDocument> dto = paginate(all, page, size); // [수정 전]
        PageResponse<LegalDocument> dto = paginate(filteredList, page, size); // [수정 후]
        return ResponseEntity.ok(dto);
    }

    /**
     * 법령 키워드 검색 (제목+내용)
     * GET /api/LegalDocument/search?keyword=...&page=0&size=20
     * - size <= 0: 리스트로 반환
     * - size > 0 : PageResponse로 반환
     */
    @GetMapping("/search")
    public ResponseEntity<?> search(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        // 서비스에서 전체 검색 결과 리스트를 가져온 뒤 컨트롤러에서 수동 페이징
        List<LegalDocument> all = legalDocumentService.search(keyword);

        // --- [추가] lawId 기준으로 중복 제거 ---
        // LinkedHashMap: 키가 삽입된 순서를 유지하는 Map
        Map<String, LegalDocument> uniqueDocsMap = new LinkedHashMap<>();
        for (LegalDocument doc : all) {
            // putIfAbsent: 맵에 해당 lawId (키)가 없는 경우에만 문서를 추가합니다.
            // (결과적으로, 같은 lawId 중 가장 처음에 나온 문서만 맵에 남게 됩니다.)
            uniqueDocsMap.putIfAbsent(doc.getLawId(), doc);
        }
        // 맵에 저장된 값(LegalDocument)들만 다시 리스트로 변환합니다.
        List<LegalDocument> filteredList = new ArrayList<>(uniqueDocsMap.values());
        // --- 중복 제거 끝 ---


        if (size <= 0) {
            // return ResponseEntity.ok(all); // [수정 전]
            return ResponseEntity.ok(filteredList); // [수정 후] 중복 제거된 리스트 반환
        }

        // PageResponse<LegalDocument> dto = paginate(all, page, size); // [수정 전]
        PageResponse<LegalDocument> dto = paginate(filteredList, page, size); // [수정 후] 중복 제거된 리스트로 페이징
        return ResponseEntity.ok(dto);
    }
    
    /**
     * 공통 수동 페이징 유틸
     */
    private static <T> PageResponse<T> paginate(List<T> all, int page, int size) {
        if (page < 0) page = 0;
        if (size <= 0) size = 20;

        int total = all != null ? all.size() : 0;
        if (total == 0) {
            return new PageResponse<>(Collections.emptyList(), 0, 0, page, size);
        }

        int fromIndex = page * size;
        if (fromIndex >= total) {
            return new PageResponse<>(Collections.emptyList(), total, (int) Math.ceil((double) total / size), page, size);
        }

        int toIndex = Math.min(fromIndex + size, total);
        List<T> content = all.subList(fromIndex, toIndex);
        int totalPages = (int) Math.ceil((double) total / size);

        return new PageResponse<>(content, total, totalPages, page, size);
    }
}