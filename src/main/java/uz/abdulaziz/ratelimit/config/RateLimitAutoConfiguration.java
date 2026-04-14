package uz.abdulaziz.ratelimit.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import uz.abdulaziz.ratelimit.core.service.InMemoryRateLimiterService;
import uz.abdulaziz.ratelimit.core.service.RateLimiterService;
import uz.abdulaziz.ratelimit.core.service.RedisRateLimiterService;

@Configuration
@EnableConfigurationProperties(RateLimitProperties.class)
public class RateLimitAutoConfiguration {

    @Bean
    public RateLimiterService rateLimiterService(
            RateLimitProperties props,
            org.springframework.beans.factory.ObjectProvider<StringRedisTemplate> redisProvider
    ) {

        StringRedisTemplate redis = redisProvider.getIfAvailable();

        if (props.getStorage() == StorageType.REDIS) {
            if (redis == null) {
                throw new RuntimeException("Redis selected but not configured");
            }
            return new RedisRateLimiterService(redis);
        }

        if (props.getStorage() == StorageType.MEMORY) {
            return new InMemoryRateLimiterService();
        }

        // AUTO
        if (redis != null) {
            return new RedisRateLimiterService(redis);
        }

        return new InMemoryRateLimiterService();
    }
}