package JavaProject.Backend.service;

import JavaProject.Backend.domain.Situation;
import JavaProject.Backend.repository.SituationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SituationService {
    
    private final SituationRepository situationRepository;
    
    /**
     * 활성 상황 전체 조회 (정렬 순서대로)
     * @return 활성화된 상황 목록
     */
    public List<Situation> getAllActiveSituations() {
        return situationRepository.findByActiveTrueOrderByDisplayOrderAsc();
    }
    
    /**
     * 카테고리별 활성 상황 조회
     * @param category 카테고리명 (예: "노동", "주거")
     * @return 해당 카테고리의 활성 상황 목록
     */
    public List<Situation> getSituationsByCategory(String category) {
        return situationRepository.findByCategoryAndActiveTrue(category);
    }
    
    /**
     * 상황 ID로 조회
     * @param situationId 상황 ID
     * @return Situation (Optional)
     */
    public Optional<Situation> getSituationById(String situationId) {
        return situationRepository.findById(situationId);
    }
    
    /**
     * 상황 생성 (관리자용)
     * @param situation 생성할 상황 정보
     * @return 생성된 Situation
     */
    public Situation createSituation(Situation situation) {
        return situationRepository.save(situation);
    }
    
    /**
     * 상황 수정 (관리자용)
     * @param situationId 상황 ID
     * @param updatedSituation 수정할 상황 정보
     * @return 수정된 Situation
     */
    public Situation updateSituation(String situationId, Situation updatedSituation) {
        Situation situation = situationRepository.findById(situationId)
                .orElseThrow(() -> new IllegalArgumentException("상황을 찾을 수 없습니다."));
        
        situation.setTitle(updatedSituation.getTitle());
        situation.setDescription(updatedSituation.getDescription());
        situation.setCategory(updatedSituation.getCategory());
        situation.setActive(updatedSituation.getActive());
        situation.setDisplayOrder(updatedSituation.getDisplayOrder());
        
        return situationRepository.save(situation);
    }
}
