package uz.abdulaziz.ratelimit.aspect;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import uz.abdulaziz.ratelimit.core.annotation.RateLimit;
import uz.abdulaziz.ratelimit.core.exception.RateLimitExceededException;
import uz.abdulaziz.ratelimit.core.model.Decision;
import uz.abdulaziz.ratelimit.core.resolver.KeyResolver;
import uz.abdulaziz.ratelimit.core.service.RateLimiterService;

@Aspect
@Component
public class RateLimitAspect {

    private final RateLimiterService service;
    private final KeyResolver resolver;

    public RateLimitAspect(
            RateLimiterService service,
            KeyResolver resolver
    ) {
        this.service = service;
        this.resolver = resolver;
    }

    @Around("@annotation(rateLimit)")
    public Object around(ProceedingJoinPoint pjp,
                         RateLimit rateLimit) throws Throwable {

        RequestAttributes requestAttributes =
                RequestContextHolder.currentRequestAttributes();

        ServletRequestAttributes attrs =
                (ServletRequestAttributes) requestAttributes;

        HttpServletRequest request = attrs.getRequest();
        HttpServletResponse response = attrs.getResponse();

        String key = resolver.resolve(request, rateLimit);

        Decision decision = service.check(
                key,
                rateLimit.limit(),
                rateLimit.duration()
        );

        if (response != null) {
            response.setHeader(
                    "X-RateLimit-Limit",
                    String.valueOf(rateLimit.limit())
            );

            response.setHeader(
                    "X-RateLimit-Remaining",
                    String.valueOf(decision.remaining())
            );

            response.setHeader(
                    "Retry-After",
                    String.valueOf(decision.retryAfter())
            );
        }

        if (!decision.allowed()) {
            throw new RateLimitExceededException();
        }

        return pjp.proceed();
    }
}