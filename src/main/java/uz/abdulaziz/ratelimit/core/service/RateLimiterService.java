package uz.abdulaziz.ratelimit.core.service;

import uz.abdulaziz.ratelimit.core.model.Decision;

public interface RateLimiterService {
    Decision check(String key, int limit, int duration);
}