package JavaProject.Backend.service;

import JavaProject.Backend.domain.LegalDocument;
import JavaProject.Backend.domain.RelatedLaw;
import JavaProject.Backend.domain.Situation;
import JavaProject.Backend.repository.LegalDocumentRepository;
import JavaProject.Backend.repository.SituationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SituationService {

    private final SituationRepository situationRepository;
    private final LegalDocumentRepository legalDocumentRepository;

    // 1) 전체 조회
    public List<Situation> getAllSituations() {
        return situationRepository.findAll();
    }

    // 2) 키워드 검색 (제목 + 설명 + 요약 + 카테고리 통합 검색)
    public List<Situation> searchSituations(String keyword) {

        List<Situation> result = new ArrayList<>();
        Set<String> addedIds = new HashSet<>();

        // [1] 제목
        List<Situation> titleList =
                situationRepository.findByTitleContainingIgnoreCase(keyword);
        for (Situation s : titleList) {
            if (s.getId() != null && addedIds.add(s.getId())) {
                result.add(s);
            }
        }

        // [2] 설명
        List<Situation> descList =
                situationRepository.findByDescriptionContainingIgnoreCase(keyword);
        for (Situation s : descList) {
            if (s.getId() != null && addedIds.add(s.getId())) {
                result.add(s);
            }
        }

        // [3] 요약(summary)
        List<Situation> summaryList =
                situationRepository.findBySummaryContainingIgnoreCase(keyword);
        for (Situation s : summaryList) {
            if (s.getId() != null && addedIds.add(s.getId())) {
                result.add(s);
            }
        }

        // [4] 카테고리
        List<Situation> categoryList =
                situationRepository.findByCategoryContainingIgnoreCase(keyword);
        for (Situation s : categoryList) {
            if (s.getId() != null && addedIds.add(s.getId())) {
                result.add(s);
            }
        }

        return result;
    }

    // 3) 상세 조회
    public Situation getSituationById(String id) {

        Situation situation = situationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("상황을 찾을 수 없습니다: " + id));

        // 관련 법령 제목 자동 매핑
        if (situation.getRelatedLaws() != null) {
            for (RelatedLaw rl : situation.getRelatedLaws()) {

                if (rl.getLawId() != null && !rl.getLawId().trim().isEmpty()) {

                    legalDocumentRepository
                            .findFirstByLawId(rl.getLawId())
                            .ifPresentOrElse(
                                    doc -> rl.setTitle(doc.getTitle()),
                                    () -> rl.setTitle("(제목 없음)")
                            );
                }
            }
        }

        return situation;
    }
}
