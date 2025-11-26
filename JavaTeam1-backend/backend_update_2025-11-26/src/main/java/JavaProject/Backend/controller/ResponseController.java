package JavaProject.Backend.controller;

import JavaProject.Backend.domain.UserResponse;
import JavaProject.Backend.repository.UserResponseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.bson.types.ObjectId;
import java.time.LocalDateTime; // [수정] import 추가
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors; // [수정] import 추가

@RestController
@RequestMapping("/responses")
@RequiredArgsConstructor
public class ResponseController {
    
    private final UserResponseRepository userResponseRepository;
    
    /**
     * 사용자 응답 저장
     * POST /api/responses
     * Body: { sessionId, userId?, situationId, questionId, responseValue, orderIndex }
     */
    @PostMapping
    public ResponseEntity<UserResponse> saveResponse(@RequestBody Map<String, String> request) {
        
        UserResponse response = UserResponse.builder()
                .sessionId(request.get("sessionId"))
                .userId(request.get("userId")) // null 가능
                .situationId(new ObjectId(request.get("situationId")))
                .questionId(request.get("questionId"))
                .responseValue(request.get("responseValue"))
                .orderIndex(Integer.parseInt(request.getOrDefault("orderIndex", "0")))
                .createdAt(LocalDateTime.now())
                .build();
        
        UserResponse saved = userResponseRepository.save(response);
        return ResponseEntity.ok(saved);
    }
    
    /**
     * 여러 응답 한 번에 저장
     * POST /api/responses/batch
     */
    @PostMapping("/batch")
    public ResponseEntity<List<UserResponse>> saveResponses(@RequestBody Map<String, Object> request) {
        String sessionId = (String) request.get("sessionId");
        String userId = (String) request.get("userId");
        String situationIdString = (String) request.get("situationId");
        
        ObjectId situationId = new ObjectId(situationIdString);
        
        @SuppressWarnings("unchecked")
        List<Map<String, String>> responsesList = (List<Map<String, String>>) request.get("responses");
        
        List<UserResponse> responses = responsesList.stream()
                .map(r -> UserResponse.builder()
                        .sessionId(sessionId)
                        .userId(userId)
                        .situationId(situationId) 
                        .questionId(r.get("questionId"))
                        .responseValue(r.get("responseValue"))
                        .orderIndex(Integer.parseInt(r.getOrDefault("orderIndex", "0"))) 
                        .createdAt(LocalDateTime.now()) 
                        .build())
                .collect(Collectors.toList()); 
        
        List<UserResponse> saved = userResponseRepository.saveAll(responses);
        return ResponseEntity.ok(saved);
    }
    
    /**
     * 세션별 응답 조회
     * GET /api/responses/session/{sessionId}
     */
    @GetMapping("/session/{sessionId}")
    public ResponseEntity<List<UserResponse>> getResponsesBySession(@PathVariable String sessionId) {
        List<UserResponse> responses = userResponseRepository.findBySessionId(sessionId);
        return ResponseEntity.ok(responses);
    }
    
    /**
     * 사용자별 응답 조회
     * GET /api/responses/user/{userId}
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserResponse>> getResponsesByUser(@PathVariable String userId) {
        List<UserResponse> responses = userResponseRepository.findByUserIdOrderByCreatedAtDesc(userId);
        return ResponseEntity.ok(responses);
    }
}