package JavaProject.Backend.service;

import JavaProject.Backend.domain.AnalysisResult;
import JavaProject.Backend.domain.UserResponse;
import JavaProject.Backend.repository.AnalysisResultRepository;
import JavaProject.Backend.repository.UserResponseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnalysisService {
    
    private final UserResponseRepository userResponseRepository;
    private final AnalysisResultRepository analysisResultRepository;
    
    /**
     * 사용자 응답 분석 및 결과 생성
     * @param sessionId 세션 ID
     * @param userId 사용자 ID (로그인 사용자인 경우, null 가능)
     * @param situationId 상황 ID
     * @return 생성된 AnalysisResult
     */
    public AnalysisResult analyzeResponses(String sessionId, String userId, String situationId) {
        // 1. 사용자 응답 조회
        List<UserResponse> responses = userResponseRepository.findBySessionIdAndSituationId(sessionId, situationId);
        
        if (responses.isEmpty()) {
            throw new IllegalArgumentException("응답 데이터가 없습니다.");
        }
        
        // 2. 응답을 Map으로 변환 (questionId -> responseValue)
        Map<String, String> answerMap = responses.stream()
                .collect(Collectors.toMap(
                        UserResponse::getQuestionId,
                        UserResponse::getResponseValue
                ));
        
        // 3. 시나리오 키 생성 (답변 조합에 따라)
        String scenarioKey = determineScenario(situationId, answerMap);
        
        // 4. 시나리오에 맞는 결과 템플릿 로드
        // TODO: JSON 파일 또는 DB에서 결과 템플릿 로드
        // 현재는 임시 데이터로 대체
        AnalysisResult result = buildAnalysisResult(sessionId, userId, situationId, scenarioKey);
        
        // 5. 결과 저장
        return analysisResultRepository.save(result);
    }
    
    /**
     * 세션별 분석 결과 조회
     * @param sessionId 세션 ID
     * @return 분석 결과 목록
     */
    public List<AnalysisResult> getResultsBySession(String sessionId) {
        return analysisResultRepository.findBySessionIdOrderByCreatedAtDesc(sessionId);
    }
    
    /**
     * 사용자별 분석 결과 조회
     * @param userId 사용자 ID
     * @return 분석 결과 목록
     */
    public List<AnalysisResult> getResultsByUser(String userId) {
        return analysisResultRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }
    
    /**
     * 사용자별 분석 결과 조회 (페이징)
     * @param userId 사용자 ID
     * @param pageable 페이징 정보
     * @return 분석 결과 페이지
     */
    public Page<AnalysisResult> getResultsByUser(String userId, Pageable pageable) {
        return analysisResultRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable);
    }
    
    /**
     * 분석 결과 ID로 조회
     * @param resultId 결과 ID
     * @return AnalysisResult (Optional)
     */
    public Optional<AnalysisResult> getResultById(String resultId) {
        return analysisResultRepository.findById(resultId);
    }
    
    // ===== 내부 메서드 =====
    
    /**
     * 답변 조합에 따라 시나리오 키 결정
     * @param situationId 상황 ID
     * @param answerMap 질문ID -> 답변 맵
     * @return 시나리오 키
     */
    private String determineScenario(String situationId, Map<String, String> answerMap) {
        // TODO: 비즈니스 로직에 따라 시나리오 키 생성
        // 예: wage_delay_has_contract_1month
        
        // 임시 로직
        StringBuilder key = new StringBuilder(situationId);
        answerMap.forEach((qId, answer) -> {
            key.append("_").append(answer.toLowerCase().replace(" ", "_"));
        });
        
        return key.toString();
    }
    
    /**
     * 시나리오에 맞는 분석 결과 생성
     * @param sessionId 세션 ID
     * @param userId 사용자 ID
     * @param situationId 상황 ID
     * @param scenarioKey 시나리오 키
     * @return AnalysisResult
     */
    private AnalysisResult buildAnalysisResult(String sessionId, String userId, String situationId, String scenarioKey) {
        // TODO: JSON 파일 또는 DB에서 템플릿 로드
        // 현재는 임시 데이터
        
        return AnalysisResult.builder()
                .sessionId(sessionId)
                .userId(userId)
                .situationId(situationId)
                .resultSummary("근로계약서가 있다면 노동청 진정 절차를 진행할 수 있습니다.")
                .procedures(Arrays.asList(
                        "1단계: 사업주에게 서면으로 임금 지급 요청",
                        "2단계: 노동청에 진정서 제출",
                        "3단계: 증거자료 준비"
                ))
                .checklist(Arrays.asList(
                        "근로계약서 사본",
                        "임금명세서",
                        "출근기록"
                ))
                .relatedLaws(Arrays.asList(
                        AnalysisResult.RelatedLawInfo.builder()
                                .lawId("LAW-001")
                                .title("근로기준법")
                                .relevantArticles(Arrays.asList("제36조", "제43조"))
                                .build()
                ))
                .pdfGenerationStatus("READY")
                .build();
    }
}
