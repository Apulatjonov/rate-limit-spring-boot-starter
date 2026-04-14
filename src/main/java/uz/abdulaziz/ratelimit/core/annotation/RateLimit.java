package uz.abdulaziz.ratelimit.core.annotation;

import uz.abdulaziz.ratelimit.core.strategy.KeyStrategy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimit {
    KeyStrategy strategy() default KeyStrategy.ENDPOINT_AND_HEADER;

    String header() default "X-User-Id";

    int limit() default 10;

    int duration() default 60;
}