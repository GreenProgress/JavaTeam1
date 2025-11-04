package JavaProject.Backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    /**
     * CORS 설정
     * 프론트엔드에서 백엔드 API 호출을 허용
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // /api/** 경로에 대해
                .allowedOrigins(
                        "http://localhost:3000",  // React 개발 서버
                        "http://localhost:8080",  // Vue 개발 서버
                        "http://localhost:5173"   // Vite 개발 서버
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600); // preflight 캐시 1시간
    }
}
