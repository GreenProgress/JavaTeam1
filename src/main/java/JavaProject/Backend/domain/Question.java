package JavaProject.Backend.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * 질문 엔티티
 * 
 * MongoDB Collection: questions
 * 
 * 특정 상황(Situation)에 대해 사용자에게 물어볼 질문 정보
 * 예: "근로계약서를 작성했습니까?", "체불 기간은 얼마입니까?"
 * 
 * @author JP Team
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Question")
@CompoundIndexes({
    @CompoundIndex(name = "situation_order_idx", def = "{'situationId': 1, 'orderIndex': 1}")
})
public class Question {
    
    @Id
    private String id; // MongoDB 문서 고유 ID
    
    
    private String situationId; // 연결된 상황 ID (Situation.id 참조)
    
    
    private String questionText; // 질문 내용 (예: "근로계약서를 작성했습니까?")
    
    
    private String questionType; // 질문 타입
    // "yes_no": 예/아니오
    // "choice": 선택지 (choices 필드 사용)
    // "text": 텍스트 입력
    // "number": 숫자 입력
    
    
    private List<String> choices; // 선택지 목록 (questionType이 "choice"일 때)
    // 예: ["1개월 이하", "2개월 이상"]
    
    
    private Integer orderIndex; // 질문 순서 (화면에 표시할 순서, 작은 숫자가 먼저 표시됨)
    
    
    @Builder.Default
    private Boolean active = true; // 질문 활성화 여부 (true: 사용자에게 노출, false: 숨김)
    
    
    private String helpText; // 도움말 텍스트 (선택사항)
    // 예: "근로계약서가 없다면 임금명세서나 출근기록으로 대체할 수 있습니다."
    
}
