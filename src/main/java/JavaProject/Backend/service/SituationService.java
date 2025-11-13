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
     * [신규] 모든 상황 조회 (컨트롤러 Error 3 해결용)
     * @return 모든 상황 목록
     */
    public List<Situation> getAllSituations() {
        return situationRepository.findAll();
    }
    
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
    // [수정] 컨트롤러 Error 1(Type mismatch) 해결: Optional<Situation> -> Situation
    public Situation getSituationById(String situationId) {
        // .orElse(null)을 통해 Optional을 벗겨내고 Situation 객체나 null을 반환
        return situationRepository.findById(situationId).orElse(null);
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
    
    /**
     * [신규] 상황 검색 서비스 메서드 (Home.jsx 지원)
     * @param keyword 검색어
     * @return 검색된 Situation 리스트
     */
    public List<Situation> searchSituations(String keyword) {
        // (SituationRepository에 findByTitleContainingOrDescriptionContaining이 정의되어 있어야 함)
        return situationRepository.findByTitleContainingOrDescriptionContaining(keyword, keyword);
    }
}