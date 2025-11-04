package JavaProject.Backend.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 법령 문서 엔티티
 * 
 * MongoDB Collection: LegalDocument
 * 
 * 법령 조항별 세부 정보를 저장
 * 
 * @author JP Team
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "LegalDocument")
public class LegalDocument {
    
    @Id
    private String id; // MongoDB 문서 고유 ID (_id)
    
    private String lawKey; // 법령 키 (예: "0093382025021135257")
    
    
    @Indexed
    private String lawId; // 법령 코드 (예: "009338")
    
    
    @TextIndexed(weight = 10)
    private String title; // 법령 제목 (예: "전자상거래 등에서의 소비자보호에 관한 법률 시행령")
    
    
    private String articleNo; // 세부조항 번호 (예: "15")
    
    
    private String articleTitle; // 세부조항 제목 (예: "통신판매업자의 신고사항")
    
    
    @TextIndexed(weight = 5)
    private String articleText; // 세부조항 내용 (예: "제15조(통신판매업자의 신고사항) ...")
    
    
    private String sourceUrl; // 출처 URL
    
    
    private String lastUpdated; // 업데이트 날짜 (String 형식)
    
    
    private String categori; // 구분 카테고리 (예: "노동")
    
    
}
