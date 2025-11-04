package JavaProject.Backend.controller;

import JavaProject.Backend.domain.LegalDocument;
import JavaProject.Backend.service.LegalDocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/LegalDocument")
@RequiredArgsConstructor
public class LegalDocumentController {
    
    private final LegalDocumentService legalDocumentService;
    
    /**
     * 법령 목록 조회 (content 제외)
     * GET /api/LegalDocument
     */
    @GetMapping
    public ResponseEntity<List<LegalDocument>> getAllDocuments() {
        List<LegalDocument> documents = legalDocumentService.getAllDocuments();
        return ResponseEntity.ok(documents);
    }
    
    /**
     * 특정 법령 상세 조회
     * GET /api/LegalDocument{lawId}
     */
    @GetMapping("/{lawId}")
    public ResponseEntity<List<LegalDocument>> getDocumentsByLawId(@PathVariable String lawId) {
        List<LegalDocument> documents = legalDocumentService.getDocumentsByLawId(lawId);
        
        if (documents.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(documents);
    }
    
    /**
     * 카테고리별 법령 조회
     * GET /api/LegalDocument/categori/{category}
     */
    @GetMapping("/categori/{categori}")
    public ResponseEntity<?> getDocumentsByCategory(
            @PathVariable String categori,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        
        if (size > 0) {
            // 페이징
            Pageable pageable = PageRequest.of(page, size);
            Page<LegalDocument> documents = legalDocumentService.getDocumentsByCategori(categori, pageable);
            return ResponseEntity.ok(documents);
        } else {
            // 전체
            List<LegalDocument> documents = legalDocumentService.getDocumentsByCategori(categori);
            return ResponseEntity.ok(documents);
        }
    }
    
    /**
     * 법령 검색 (제목 + 내용)
     * GET /api/LegalDocument/search?keyword=임금
     */
    @GetMapping("/search")
    public ResponseEntity<?> searchDocuments(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        
        if (size > 0) {
            // 페이징
            Pageable pageable = PageRequest.of(page, size);
            Page<LegalDocument> documents = legalDocumentService.searchByKeyword(keyword, pageable);
            return ResponseEntity.ok(documents);
        } else {
            // 전체
            List<LegalDocument> documents = legalDocumentService.searchByKeyword(keyword);
            return ResponseEntity.ok(documents);
        }
    }
    
    /**
     * 법령 생성 (관리자용)
     * POST /api/LegalDocument
     */
    @PostMapping
    public ResponseEntity<LegalDocument> createDocument(@RequestBody LegalDocument document) {
        LegalDocument created = legalDocumentService.createDocument(document);
        return ResponseEntity.ok(created);
    }
    
    /**
     * 법령 수정 (관리자용)
     * PUT /api/LegalDocument/{lawId}
     */
    @PutMapping("/{lawId}")
    public ResponseEntity<LegalDocument> updateDocument(
            @PathVariable String lawId,
            @RequestBody LegalDocument document) {
        
        LegalDocument updated = legalDocumentService.updateDocument(lawId, document);
        return ResponseEntity.ok(updated);
    }
}
