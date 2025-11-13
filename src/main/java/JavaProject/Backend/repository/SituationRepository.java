package JavaProject.Backend.repository;

import JavaProject.Backend.domain.Situation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SituationRepository extends MongoRepository<Situation, String> {
    
    // 카테고리별 활성 상황 조회
    List<Situation> findByCategoryAndActiveTrue(String categori);
    
    // 활성 상황 전체 조회 (정렬 순서 적용)
    List<Situation> findByActiveTrueOrderByDisplayOrderAsc();
    
    // 카테고리 목록 조회 (중복 제거)
    // @Query(value = "{}", fields = "{'category': 1}")
    // List<String> findDistinctCategories();
    
    // 키워드로 상황 제목 또는 설명을 검색하는 메서드
    List<Situation> findByTitleContainingOrDescriptionContaining(String title, String description);
}
