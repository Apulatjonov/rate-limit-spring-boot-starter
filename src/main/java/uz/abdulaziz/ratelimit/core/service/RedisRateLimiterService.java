package uz.abdulaziz.ratelimit.core.service;

import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import uz.abdulaziz.ratelimit.core.model.Decision;

import java.time.Duration;

@Component
@Primary
public class RedisRateLimiterService implements RateLimiterService {
    private final StringRedisTemplate redis;

    public RedisRateLimiterService(StringRedisTemplate redis) {
        this.redis = redis;
    }

    public Decision check(String key, int limit, int duration) {
        Long count = redis.opsForValue().increment(key);
        if (count != null && count == 1) redis.expire(key, Duration.ofSeconds(duration));
        Long ttl = redis.getExpire(key);
        boolean ok = count != null && count <= limit;
        return new Decision(ok, Math.max(0, limit - (count == null ? 0 : count.intValue())), ttl == null ? 0 : ttl);
    }
}