package JavaProject.Backend.repository;

import JavaProject.Backend.domain.Question;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends MongoRepository<Question, String> {
    
    // 특정 상황의 활성 질문 목록 조회 (순서대로)
    List<Question> findBySituationIdAndActiveTrueOrderByOrderIndexAsc(String situationId);
    
    // 특정 상황의 모든 질문 조회 (관리자용)
    List<Question> findBySituationIdOrderByOrderIndexAsc(String situationId);
    
    // 특정 상황의 질문 개수 조회
    long countBySituationIdAndActiveTrue(String situationId);
}
