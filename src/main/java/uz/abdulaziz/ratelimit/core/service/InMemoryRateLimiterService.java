package uz.abdulaziz.ratelimit.core.service;

import org.springframework.stereotype.Component;
import uz.abdulaziz.ratelimit.core.model.Decision;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class InMemoryRateLimiterService implements RateLimiterService {
    private final ConcurrentHashMap<String, AtomicInteger> map = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Long> exp = new ConcurrentHashMap<>();

    public Decision check(String key, int limit, int duration) {
        long now = System.currentTimeMillis();
        exp.compute(key, (k, v) -> v == null || v < now ? now + duration * 1000L : v);
        if (exp.get(key) < now) {
            map.remove(key);
        }
        int c = map.computeIfAbsent(key, k -> new AtomicInteger()).incrementAndGet();
        return new Decision(c <= limit, Math.max(0, limit - c), (exp.get(key) - now) / 1000);
    }
}