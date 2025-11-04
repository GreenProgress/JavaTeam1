package JavaProject.Backend.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

/**
 * 분석 결과 엔티티
 * 
 * MongoDB Collection: analysis_results
 * 
 * 사용자의 응답을 분석하여 생성된 결과
 * - 결론 요약
 * - 행동 절차 단계
 * - 필요 서류 체크리스트
 * - 관련 법령 정보
 * 
 * @author JP Team
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "AnalysisResult")
@CompoundIndexes({
    @CompoundIndex(name = "user_created_idx", def = "{'userId': 1, 'createdAt': -1}")
})
public class AnalysisResult {
    
	
    @Id
    private String id; // MongoDB 문서 고유 ID
    
    
    private String userId; // 사용자 ID (로그인 사용자인 경우)
    // User.id 참조, null일 경우 비로그인 사용자


    @Indexed
    private String sessionId; // 세션 ID, 비로그인 사용자 식별용


    @Indexed
    private String situationId; // 상황 ID
    // Situation.id 참조


    private String resultSummary; // 결론 요약
    // 예: "근로계약서가 있다면 노동청 진정 절차를 진행할 수 있습니다."


    private List<String> procedures; // 행동 절차 단계 목록
    // 예: ["1단계: 사업주에게 서면 요청", "2단계: 노동청 진정서 제출"]


    private List<String> checklist; // 필요 서류 체크리스트
    // 예: ["근로계약서 사본", "임금명세서", "출근기록"]


    private List<RelatedLawInfo> relatedLaws; // 관련 법령 정보 목록


    @Builder.Default
    private String pdfGenerationStatus = "READY"; // PDF 생성 상태
    // "READY": 생성 대기
    // "PROCESSING": 생성 중
    // "COMPLETED": 생성 완료
    // "FAILED": 생성 실패
    

    private String pdfFilePath; // PDF 파일 경로
    // 예: "/pdf/2025/11/ar_001.pdf"


    @CreatedDate // @CreatedDate로 자동 설정
    private Date createdAt; // 생성 일시


    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RelatedLawInfo { // 관련 법령 정보 내부 클래스


        private String lawId; // 법령 ID
        // LegalDocument.lawId 참조


        private String title; // 법령 제목
        // 예: "근로기준법"

        
        private List<String> relevantArticles; // 관련 조항 목록 (선택사항)
        // 예: ["제36조", "제43조"]
        
        
    }
}
