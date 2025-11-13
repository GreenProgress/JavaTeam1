package JavaProject.Backend.repository;

import JavaProject.Backend.domain.UserResponse;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.bson.types.ObjectId;
import java.util.List;

@Repository
public interface UserResponseRepository extends MongoRepository<UserResponse, String> {
    
    // 세션 ID로 응답 조회 (비로그인 사용자)
    List<UserResponse> findBySessionId(String sessionId);
    
    // 세션 ID + 상황 ID로 응답 조회
    List<UserResponse> findBySessionIdAndSituationId(String sessionId, ObjectId situationId);

    // 세션 ID + 상황 ID로 응답 조회 (최신순 정렬)
    List<UserResponse> findBySessionIdAndSituationIdOrderByCreatedAtDesc(String sessionId, ObjectId situationId);
    
    // 사용자 ID로 응답 조회 (로그인 사용자)
    List<UserResponse> findByUserIdOrderByCreatedAtDesc(String userId);
    
    // 사용자 ID + 상황 ID로 응답 조회
    List<UserResponse> findByUserIdAndSituationId(String userId, String situationId);
    
    // 특정 질문에 대한 응답 존재 여부 확인
    boolean existsBySessionIdAndQuestionId(String sessionId, String questionId);
}