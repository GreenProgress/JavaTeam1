package JavaProject.Backend.exception;

/**
 * 인증되지 않은 접근 시도 시 발생하는 예외
 * HTTP 401 Unauthorized
 */
public class UnauthorizedException extends RuntimeException {
    
    public UnauthorizedException(String message) {
        super(message);
    }
    
    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }
}
