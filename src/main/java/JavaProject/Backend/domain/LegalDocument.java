package JavaProject.Backend.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

/**
 * 법령 문서 엔티티
 * 
 * MongoDB Collection: legal_documents
 * 
 * 법령 원문과 메타데이터를 저장
 * 텍스트 인덱스를 통한 전문 검색 지원
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
    private String id; // MongoDB 문서 고유 ID


    @Indexed(unique = true) // 유니크 제약 조건
    private String lawId; // 법령 코드
    // 예: "LAW-001"


    @TextIndexed(weight = 10) // 텍스트 인덱스 적용 (가중치 10)
    private String title; // 법령 제목
    // 예: "근로기준법"

    
    @TextIndexed(weight = 5) // 텍스트 인덱스 적용 (가중치 5)
    private String content; // 법령 원문 전체
    // 예: "제1조(목적) 이 법은 헌법에 따라..."
    

    private String sourceUrl; // 출처 URL
    // 예: "https://www.law.go.kr/LSW/lsInfoP.do?lsiSeq=123456"


    @LastModifiedDate // @LastModifiedDate로 자동 갱신
    private Date lastUpdated; // 마지막 갱신 일시


    private List<String> categories; // 카테고리 목록
    // 예: ["노동", "임대차"]

    
}
