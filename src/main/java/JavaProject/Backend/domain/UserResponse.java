package JavaProject.Backend.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * 사용자 응답 엔티티
 * 
 * MongoDB Collection: user_responses
 * 
 * 사용자가 질문에 대해 입력한 답변을 저장
 * 세션 또는 로그인 사용자 기준으로 추적
 * 
 * @author JP Team
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "UserResponse")
@CompoundIndexes({
    @CompoundIndex(name = "user_responded_idx", def = "{'userId': 1, 'respondedAt': -1}"),
    @CompoundIndex(name = "situation_question_idx", def = "{'situationId': 1, 'questionId': 1}")
})
public class UserResponse {
    
    @Id
    private String id; // MongoDB 문서 고유 ID
    
    
    private String userId; // 사용자 ID (로그인 사용자인 경우)
    // User.id 참조, null일 경우 비로그인 사용자
    
    
    @Indexed
    private String sessionId; // 세션 ID
    // 비로그인 사용자 식별용, 브라우저 세션 단위로 생성
    
    
    private String situationId; // 상황 ID
    // Situation.id 참조


    private String questionId; // 질문 ID
    // Question.id 참조
    
    
    private String responseValue; // 사용자의 답변 값
    // yes_no: "yes" 또는 "no"
    // choice: 선택된 선택지 텍스트
    // text: 사용자가 입력한 텍스트
    // number: 숫자 문자열
    
    
    @CreatedDate // @CreatedDate로 자동 설정
    private Date respondedAt; // 응답 일시

    
    
}
