package JavaProject.Backend.repository;

import JavaProject.Backend.domain.AnalysisResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnalysisResultRepository extends MongoRepository<AnalysisResult, String> {
    
    // 세션 ID로 결과 조회 (비로그인 사용자)
    List<AnalysisResult> findBySessionIdOrderByCreatedAtDesc(String sessionId);
    
    // 사용자 ID로 결과 조회 (로그인 사용자, 최신순)
    List<AnalysisResult> findByUserIdOrderByCreatedAtDesc(String userId);
    
    // 사용자 ID로 결과 조회 (페이징)
    Page<AnalysisResult> findByUserIdOrderByCreatedAtDesc(String userId, Pageable pageable);
    
    // 사용자 ID + 상황 ID로 결과 조회
    List<AnalysisResult> findByUserIdAndSituationIdOrderByCreatedAtDesc(String userId, String situationId);
    
    // PDF 생성 대기 중인 결과 조회
    List<AnalysisResult> findByPdfGenerationStatus(String status);
    
    // 특정 세션의 최신 결과 1개 조회
    Optional<AnalysisResult> findFirstBySessionIdOrderByCreatedAtDesc(String sessionId);
}
