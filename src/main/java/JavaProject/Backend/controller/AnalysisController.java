package JavaProject.Backend.controller;

import JavaProject.Backend.domain.AnalysisResult;
import JavaProject.Backend.service.AnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/analysis")
@RequiredArgsConstructor
public class AnalysisController {
    
    private final AnalysisService analysisService;
    
    /**
     * 분석 요청 (응답 기반 결과 생성)
     * POST /api/analysis
     * Body: { sessionId, userId?, situationId }
     */
    @PostMapping
    public ResponseEntity<AnalysisResult> analyzeResponses(@RequestBody Map<String, String> request) {
        String sessionId = request.get("sessionId");
        String userId = request.get("userId"); // null 가능
        String situationId = request.get("situationId");
        
        AnalysisResult result = analysisService.analyzeResponses(sessionId, userId, situationId);
        return ResponseEntity.ok(result);
    }
    
    /**
     * 특정 분석 결과 조회
     * GET /api/analysis/{resultId}
     */
    @GetMapping("/{resultId}")
    public ResponseEntity<AnalysisResult> getResult(@PathVariable String resultId) {
        return analysisService.getResultById(resultId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * 세션별 분석 결과 목록 조회
     * GET /api/analysis/session/{sessionId}
     */
    @GetMapping("/session/{sessionId}")
    public ResponseEntity<List<AnalysisResult>> getResultsBySession(@PathVariable String sessionId) {
        List<AnalysisResult> results = analysisService.getResultsBySession(sessionId);
        return ResponseEntity.ok(results);
    }
    
    /**
     * 사용자별 분석 결과 목록 조회
     * GET /api/analysis/user/{userId}
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getResultsByUser(
            @PathVariable String userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        if (size > 0) {
            // 페이징 조회
            Pageable pageable = PageRequest.of(page, size);
            Page<AnalysisResult> results = analysisService.getResultsByUser(userId, pageable);
            return ResponseEntity.ok(results);
        } else {
            // 전체 조회
            List<AnalysisResult> results = analysisService.getResultsByUser(userId);
            return ResponseEntity.ok(results);
        }
    }
    
    /**
     * PDF 생성 요청
     * POST /api/analysis/{resultId}/pdf
     */
    @PostMapping("/{resultId}/pdf")
    public ResponseEntity<?> generatePdf(@PathVariable String resultId) {
        // TODO: PdfService 구현 후 연결
        return ResponseEntity.accepted().body(Map.of(
                "message", "PDF 생성 요청이 접수되었습니다.",
                "resultId", resultId
        ));
    }
    
    /**
     * PDF 다운로드
     * GET /api/analysis/{resultId}/pdf
     */
    @GetMapping("/{resultId}/pdf")
    public ResponseEntity<?> downloadPdf(@PathVariable String resultId) {
        // TODO: PdfService 구현 후 연결
        return ResponseEntity.ok().body(Map.of(
                "message", "PDF 다운로드 기능은 구현 예정입니다.",
                "resultId", resultId
        ));
    }
}
