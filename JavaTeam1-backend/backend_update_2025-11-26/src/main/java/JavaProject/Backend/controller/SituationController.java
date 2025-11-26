package JavaProject.Backend.controller;

import JavaProject.Backend.domain.Situation;
import JavaProject.Backend.service.SituationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/situations")
public class SituationController {

    private final SituationService situationService;

    // 1) 전체 목록 조회 + 키워드 검색
    @GetMapping
    public ResponseEntity<?> searchSituations(@RequestParam(required = false) String keyword) {
        try {
            if (keyword == null || keyword.trim().isEmpty()) {
                List<Situation> all = situationService.getAllSituations();
                return ResponseEntity.ok(all);
            }

            List<Situation> result = situationService.searchSituations(keyword);
            return ResponseEntity.ok(result);

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("상황 검색 중 오류 발생");
        }
    }

    // 2) ID 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<?> getSituation(@PathVariable String id) {

        try {
            Situation situation = situationService.getSituationById(id);
            return ResponseEntity.ok(situation);

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("상황 정보를 찾을 수 없습니다.");
        }
    }
}
