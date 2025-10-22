package JavaProject.Backend;

import JavaProject.Backend.Situation;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

// MongoRepository<도메인클래스, ID타입>
public interface SituationRepository extends MongoRepository<Situation, String> {
    // 메소드 이름 규칙에 따라 쿼리를 자동으로 생성합니다.
    // 예: 제목으로 Situation 찾기
    List<Situation> findByTitle(String title);
    long deleteByTitle(String title);
}