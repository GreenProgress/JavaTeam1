package JavaProject.Backend.repository;

import JavaProject.Backend.domain.LegalDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LegalDocumentRepository extends MongoRepository<LegalDocument, String> {
    
    // 법령 코드로 조회
	List<LegalDocument> findByLawId(String lawId);
    
    // 법령 코드 목록으로 조회 (관련 법령 조회용)
    List<LegalDocument> findByLawIdIn(List<String> lawIds);
    
    // 카테고리별 법령 조회
    List<LegalDocument> findByCategori(String categori);
    
    // 카테고리별 법령 조회 (페이징)
    Page<LegalDocument> findByCategori(String categori, Pageable pageable);
    
    // lawId와 조항 번호 목록(List)으로 법령 원문 목록을 조회하는 메서드
    List<LegalDocument> findByLawIdAndArticleNoIn(String lawId, List<String> articleNos);
    
    // 제목으로 검색 (부분 일치)
    List<LegalDocument> findByTitleContaining(String keyword);
    
    // 텍스트 인덱스 기반 전문 검색
    // title + content에서 키워드 검색
    @Query("{ $text: { $search: ?0 } }")
    List<LegalDocument> searchByKeyword(String keyword);
    
    // 전문 검색 (페이징)
    @Query("{ $text: { $search: ?0 } }")
    Page<LegalDocument> searchByKeyword(String keyword, Pageable pageable);
    
    // 법령 목록 조회 (content 제외, 목록 화면용)
    @Query(value = "{}", fields = "{ 'content': 0 }")
    List<LegalDocument> findAllExcludingContent();
}
