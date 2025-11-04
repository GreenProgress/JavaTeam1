package JavaProject.Backend.service;

import JavaProject.Backend.domain.LegalDocument;
import JavaProject.Backend.repository.LegalDocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LegalDocumentService {
    
    private final LegalDocumentRepository legalDocumentRepository;
    
    /**
     * 모든 법령 조회 (content 제외, 목록용)
     * @return 법령 목록
     */
    public List<LegalDocument> getAllDocumentsExcludingContent() {
        return legalDocumentRepository.findAllExcludingContent();
    }
    
    /**
     * 법령 코드로 조회
     * @param lawId 법령 코드
     * @return LegalDocument (Optional)
     */
    public Optional<LegalDocument> getDocumentByLawId(String lawId) {
        return legalDocumentRepository.findByLawId(lawId);
    }
    
    /**
     * 법령 코드 목록으로 조회 (분석 결과의 관련 법령 조회용)
     * @param lawIds 법령 코드 목록
     * @return 법령 목록
     */
    public List<LegalDocument> getDocumentsByLawIds(List<String> lawIds) {
        return legalDocumentRepository.findByLawIdIn(lawIds);
    }
    
    /**
     * 카테고리별 법령 조회
     * @param category 카테고리명
     * @return 법령 목록
     */
    public List<LegalDocument> getDocumentsByCategory(String category) {
        return legalDocumentRepository.findByCategories(category);
    }
    
    /**
     * 카테고리별 법령 조회 (페이징)
     * @param category 카테고리명
     * @param pageable 페이징 정보
     * @return 법령 페이지
     */
    public Page<LegalDocument> getDocumentsByCategory(String category, Pageable pageable) {
        return legalDocumentRepository.findByCategories(category, pageable);
    }
    
    /**
     * 제목으로 검색
     * @param keyword 검색 키워드
     * @return 법령 목록
     */
    public List<LegalDocument> searchByTitle(String keyword) {
        return legalDocumentRepository.findByTitleContaining(keyword);
    }
    
    /**
     * 전문 검색 (제목 + 내용)
     * @param keyword 검색 키워드
     * @return 법령 목록
     */
    public List<LegalDocument> searchByKeyword(String keyword) {
        return legalDocumentRepository.searchByKeyword(keyword);
    }
    
    /**
     * 전문 검색 (페이징)
     * @param keyword 검색 키워드
     * @param pageable 페이징 정보
     * @return 법령 페이지
     */
    public Page<LegalDocument> searchByKeyword(String keyword, Pageable pageable) {
        return legalDocumentRepository.searchByKeyword(keyword, pageable);
    }
    
    /**
     * 법령 생성 (관리자용)
     * @param document 생성할 법령 정보
     * @return 생성된 LegalDocument
     */
    public LegalDocument createDocument(LegalDocument document) {
        return legalDocumentRepository.save(document);
    }
    
    /**
     * 법령 수정 (관리자용)
     * @param lawId 법령 코드
     * @param updatedDocument 수정할 법령 정보
     * @return 수정된 LegalDocument
     */
    public LegalDocument updateDocument(String lawId, LegalDocument updatedDocument) {
        LegalDocument document = legalDocumentRepository.findByLawId(lawId)
                .orElseThrow(() -> new IllegalArgumentException("법령을 찾을 수 없습니다."));
        
        document.setTitle(updatedDocument.getTitle());
        document.setContent(updatedDocument.getContent());
        document.setSourceUrl(updatedDocument.getSourceUrl());
        document.setCategories(updatedDocument.getCategories());
        
        return legalDocumentRepository.save(document);
    }
}
