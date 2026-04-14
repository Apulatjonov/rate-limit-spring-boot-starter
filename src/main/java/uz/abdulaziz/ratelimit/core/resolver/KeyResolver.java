package uz.abdulaziz.ratelimit.core.resolver;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import uz.abdulaziz.ratelimit.core.annotation.RateLimit;

import java.util.Objects;

@Component
public class KeyResolver {
    public String resolve(HttpServletRequest req, RateLimit rl) {
        String ep = req.getMethod() + ":" + req.getRequestURI();
        return switch (rl.strategy()) {
            case IP -> "rl:" + req.getRemoteAddr() + ":" + ep;
            case ENDPOINT -> "rl:" + ep;
            case ENDPOINT_AND_HEADER ->
                    "rl:" + ep + ":" + Objects.requireNonNullElse(req.getHeader(rl.header()), "anonymous");
        };
    }
}