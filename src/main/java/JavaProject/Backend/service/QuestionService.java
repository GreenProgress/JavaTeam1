package JavaProject.Backend.service;

import JavaProject.Backend.domain.Question;
import JavaProject.Backend.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService {
    
    private final QuestionRepository questionRepository;
    
    /**
     * 특정 상황의 활성 질문 목록 조회 (순서대로)
     * @param situationId 상황 ID
     * @return 질문 목록
     */
    public List<Question> getQuestionsBySituation(String situationId) {
        return questionRepository.findBySituationIdAndActiveTrueOrderByOrderIndexAsc(situationId);
    }
    
    /**
     * 질문 ID로 조회
     * @param questionId 질문 ID
     * @return Question (Optional)
     */
    public Optional<Question> getQuestionById(String questionId) {
        return questionRepository.findById(questionId);
    }
    
    /**
     * 특정 상황의 질문 개수 조회
     * @param situationId 상황 ID
     * @return 질문 개수
     */
    public long getQuestionCountBySituation(String situationId) {
        return questionRepository.countBySituationIdAndActiveTrue(situationId);
    }
    
    /**
     * 질문 생성 (관리자용)
     * @param question 생성할 질문 정보
     * @return 생성된 Question
     */
    public Question createQuestion(Question question) {
        return questionRepository.save(question);
    }
    
    /**
     * 질문 수정 (관리자용)
     * @param questionId 질문 ID
     * @param updatedQuestion 수정할 질문 정보
     * @return 수정된 Question
     */
    public Question updateQuestion(String questionId, Question updatedQuestion) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("질문을 찾을 수 없습니다."));
        
        question.setQuestionText(updatedQuestion.getQuestionText());
        question.setQuestionType(updatedQuestion.getQuestionType());
        question.setChoices(updatedQuestion.getChoices());
        question.setOrderIndex(updatedQuestion.getOrderIndex());
        question.setActive(updatedQuestion.getActive());
        question.setHelpText(updatedQuestion.getHelpText());
        
        return questionRepository.save(question);
    }
}
