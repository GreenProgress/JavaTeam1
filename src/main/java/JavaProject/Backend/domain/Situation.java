package JavaProject.Backend.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * 상황 엔티티
 * 
 * MongoDB Collection: situations
 * 
 * 사용자가 선택할 수 있는 법률 상황 정보를 저장
 * 예: "아르바이트 임금 체불", "전세 계약 문제", "학교 폭력 피해" 등
 * 
 * @author JP Team
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Situation")
@CompoundIndexes({
    @CompoundIndex(name = "category_active_idx", def = "{'category': 1, 'active': 1}")
})
public class Situation {
    
	
    @Id
    private String id; // MongoDB 문서 고유 ID
    
    
    private String title; // 상황 제목
    
    
    private String description; // 상황 설명
    
    
    private String category; // 카테고리
    
    
    @Builder.Default
    private Boolean active = true; // 활성화 여부 (true: 사용자에게 노출, false: 관리자만 조회가능)
    
    
    @LastModifiedDate
    private Date updatedAt; // 마지막 수정 일시 (@LastModifiedDate로 자동 갱신)
    
    
    @Builder.Default
    private Integer displayOrder = 0; // 정렬 순서 (화면 노출 순서 제어용)
    
    
}
