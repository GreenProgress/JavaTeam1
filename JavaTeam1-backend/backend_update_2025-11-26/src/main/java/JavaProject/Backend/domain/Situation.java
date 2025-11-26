package JavaProject.Backend.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "Situation")
@Getter @Setter
public class Situation {

    @Id
    private String id;

    private String title;
    private String description;

    // 🔥 검색 범위 확대를 위한 요약 필드 추가
    private String summary;

    private boolean active;
    private String updatedAt;
    private Integer displayOrder;
    private String category;

    // 예전 데이터 (사용 안 해도 됨)
    private List<String> relatedLawIds;

    // 현재 사용하는 관련법령 배열
    private List<RelatedLaw> relatedLaws;

    // 하위 문서에서 사용되는 경우 있음
    private String lawId;
}
