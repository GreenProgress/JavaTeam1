package JavaProject.Backend.controller;

import JavaProject.Backend.domain.UserResponse;
import JavaProject.Backend.repository.UserResponseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/UserResponse")
@RequiredArgsConstructor
public class ResponseController {
    
    private final UserResponseRepository userResponseRepository;
    
    /**
     * 사용자 응답 저장
     * POST /api/UserResponse
     * Body: { sessionId, userId?, situationId, questionId, responseValue }
     */
    @PostMapping
    public ResponseEntity<UserResponse> saveResponse(@RequestBody Map<String, String> request) {
        UserResponse response = UserResponse.builder()
                .sessionId(request.get("sessionId"))
                .userId(request.get("userId")) // null 가능
                .situationId(request.get("situationId"))
                .questionId(request.get("questionId"))
                .responseValue(request.get("responseValue"))
                .build();
        
        UserResponse saved = userResponseRepository.save(response);
        return ResponseEntity.ok(saved);
    }
    
    /**
     * 여러 응답 한 번에 저장
     * POST /api/UserResponse/batch
     * Body: { sessionId, userId?, situationId, responses: [{ questionId, responseValue }] }
     */
    @PostMapping("/batch")
    public ResponseEntity<List<UserResponse>> saveResponsesBatch(@RequestBody Map<String, Object> request) {
        String sessionId = (String) request.get("sessionId");
        String userId = (String) request.get("userId");
        String situationId = (String) request.get("situationId");
        
        @SuppressWarnings("unchecked")
        List<Map<String, String>> responsesList = (List<Map<String, String>>) request.get("responses");
        
        List<UserResponse> responses = responsesList.stream()
                .map(r -> UserResponse.builder()
                        .sessionId(sessionId)
                        .userId(userId)
                        .situationId(situationId)
                        .questionId(r.get("questionId"))
                        .responseValue(r.get("responseValue"))
                        .build())
                .toList();
        
        List<UserResponse> saved = userResponseRepository.saveAll(responses);
        return ResponseEntity.ok(saved);
    }
    
    /**
     * 세션별 응답 조회
     * GET /api/UserResponse/session/{sessionId}
     */
    @GetMapping("/session/{sessionId}")
    public ResponseEntity<List<UserResponse>> getResponsesBySession(@PathVariable String sessionId) {
        List<UserResponse> responses = userResponseRepository.findBySessionId(sessionId);
        return ResponseEntity.ok(responses);
    }
    
    /**
     * 사용자별 응답 조회
     * GET /api/UserResponse/user/{userId}
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserResponse>> getResponsesByUser(@PathVariable String userId) {
        List<UserResponse> responses = userResponseRepository.findByUserIdOrderByRespondedAtDesc(userId);
        return ResponseEntity.ok(responses);
    }
}
