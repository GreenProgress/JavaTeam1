package JavaProject.Backend.controller;

import JavaProject.Backend.domain.Question;
import JavaProject.Backend.domain.Situation;
import JavaProject.Backend.service.QuestionService;
import JavaProject.Backend.service.SituationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Situation")
@RequiredArgsConstructor
public class SituationController {
    
    private final SituationService situationService;
    private final QuestionService questionService;
    
    /**
     * 활성 상황 전체 조회
     * GET /api/Situation
     */
    @GetMapping
    public ResponseEntity<List<Situation>> getAllSituations(
            @RequestParam(required = false) String categori) {
        
        if (categori != null && !categori.isEmpty()) {
            // 카테고리별 조회
            return ResponseEntity.ok(situationService.getSituationsByCategory(categori));
        } else {
            // 전체 조회
            return ResponseEntity.ok(situationService.getAllActiveSituations());
        }
    }
    
    /**
     * 특정 상황 상세 조회
     * GET /api/Situation/{situationId}
     */
    @GetMapping("/{situationId}")
    public ResponseEntity<Situation> getSituation(@PathVariable String situationId) {
        return situationService.getSituationById(situationId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * 특정 상황의 질문 목록 조회
     * GET /api/Situation/{situationId}/Question
     */
    @GetMapping("/{situationId}/Question")
    public ResponseEntity<List<Question>> getQuestions(@PathVariable String situationId) {
        List<Question> Question = questionService.getQuestionsBySituation(situationId);
        return ResponseEntity.ok(Question);
    }
    
    /**
     * 상황 생성 (관리자용)
     * POST /api/Situation
     */
    @PostMapping
    public ResponseEntity<Situation> createSituation(@RequestBody Situation situation) {
        Situation created = situationService.createSituation(situation);
        return ResponseEntity.ok(created);
    }
    
    /**
     * 상황 수정 (관리자용)
     * PUT /api/Situation/{situationId}
     */
    @PutMapping("/{situationId}")
    public ResponseEntity<Situation> updateSituation(
            @PathVariable String situationId,
            @RequestBody Situation situation) {
        
        Situation updated = situationService.updateSituation(situationId, situation);
        return ResponseEntity.ok(updated);
    }
}
