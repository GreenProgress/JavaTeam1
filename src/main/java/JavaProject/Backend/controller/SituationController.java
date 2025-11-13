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

    @GetMapping
    public ResponseEntity<List<Situation>> getAllSituations() {
        List<Situation> situations = situationService.getAllActiveSituations();
        return ResponseEntity.ok(situations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Situation> getSituationById(@PathVariable String id) {
        // [수정] Error 3: Optional<Situation>을 Situation으로 변환 (Service 파일에서 수정)
        Situation situation = situationService.getSituationById(id);
        if (situation == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(situation);
    }

    @GetMapping("/{situationId}/Question")
    public ResponseEntity<List<Question>> getQuestionsBySituationId(@PathVariable String situationId) {
        // [수정] Error 2: Service가 String을 받도록 수정 (Service 파일에서 수정)
        List<Question> questions = questionService.getQuestionsBySituationId(situationId);
        return ResponseEntity.ok(questions);
    }

    /**
     * [신규] 상황 키워드 검색
     * GET /api/Situation/search?keyword=...
     */
    @GetMapping("/search")
    public ResponseEntity<List<Situation>> searchSituations(@RequestParam String keyword) {
        // [수정] searchSituations 추가 (Service 파일에서 수정)
        List<Situation> situations = situationService.searchSituations(keyword);
        return ResponseEntity.ok(situations);
    }
}