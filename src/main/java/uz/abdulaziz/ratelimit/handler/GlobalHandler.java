package uz.abdulaziz.ratelimit.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uz.abdulaziz.ratelimit.core.exception.RateLimitExceededException;

import java.util.Map;

@RestControllerAdvice
public class GlobalHandler {
    @ExceptionHandler(RateLimitExceededException.class)
    ResponseEntity<Map<String, Object>> handle() {
        return ResponseEntity.status(429).body(Map.of("code", "RATE_LIMIT_EXCEEDED", "message", "Too many requests"));
    }
}