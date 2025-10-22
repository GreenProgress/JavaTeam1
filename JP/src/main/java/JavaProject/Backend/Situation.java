package JavaProject.Backend;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

// 이 클래스가 MongoDB의 "situations" 컬렉션과 매핑됨을 나타냅니다.
@Document(collection = "situations")
public class Situation {

    @Id // 이 필드가 이 문서의 고유 ID임을 나타냅니다.
    private String id;
    private String title;
    private String category;
    private Boolean active;
    private Date updatedAt;
    

    // 생성자
    public Situation() { }
    public Situation(String title, String description) {
    	this.title = title;
    	this.description = description;
    }
    
    @Override
    public String toString() {
        return "Situation{ " +
                number + ". id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    // --- 접근자, 설정자 ---
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public int getNumber() { return number; }
    public void setNumber(int number) { this.number = number; }
}