package JavaProject.Backend.service;

import JavaProject.Backend.domain.User;
import JavaProject.Backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // SecurityConfig에서 Bean 등록 필요
    
    /**
     * 회원가입
     * @param userId 사용자 아이디
     * @param password 비밀번호 (평문)
     * @param nickname 닉네임
     * @return 생성된 User
     */
    public User register(String userId, String password, String nickname) {
        // 중복 체크
        if (userRepository.existsByUserId(userId)) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }
        
        // 비밀번호 암호화
        String passwordHash = passwordEncoder.encode(password);
        
        // User 생성
        User user = User.builder()
                .userId(userId)
                .passwordHash(passwordHash)
                .nickname(nickname)
                .roles(Arrays.asList("USER")) // 기본 권한
                .active(true)
                .build();
        
        return userRepository.save(user);
    }
    
    /**
     * 로그인 인증
     * @param userId 사용자 아이디
     * @param password 비밀번호 (평문)
     * @return 인증된 User (Optional)
     */
    public Optional<User> authenticate(String userId, String password) {
        Optional<User> userOpt = userRepository.findByUserIdAndActiveTrue(userId);
        
        if (userOpt.isEmpty()) {
            return Optional.empty();
        }
        
        User user = userOpt.get();
        
        // 비밀번호 검증
        if (passwordEncoder.matches(password, user.getPasswordHash())) {
            return Optional.of(user);
        }
        
        return Optional.empty();
    }
    
    /**
     * 사용자 조회
     * @param userId 사용자 아이디
     * @return User (Optional)
     */
    public Optional<User> getUserByUserId(String userId) {
        return userRepository.findByUserId(userId);
    }
}
