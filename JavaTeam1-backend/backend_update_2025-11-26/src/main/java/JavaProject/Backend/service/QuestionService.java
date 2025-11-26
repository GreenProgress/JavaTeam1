package JavaProject.Backend.service;

import JavaProject.Backend.domain.Question;
import JavaProject.Backend.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    public List<Question> getQuestionsBySituationId(String situationId) {
        ObjectId objectId;
        try {
            objectId = new ObjectId(situationId);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("유효하지 않은 situationId 형식입니다: " + situationId);
        }
        
        return questionRepository.findBySituationIdOrderByOrderIndexAsc(objectId);
    }
}