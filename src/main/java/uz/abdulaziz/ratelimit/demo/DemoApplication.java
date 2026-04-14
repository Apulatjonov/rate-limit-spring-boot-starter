/*
package uz.abdulaziz.ratelimit.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.abdulaziz.ratelimit.core.strategy.KeyStrategy;
import uz.abdulaziz.ratelimit.core.annotation.RateLimit;

import java.util.Map;

@SpringBootApplication
@RestController
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @RateLimit(strategy = KeyStrategy.ENDPOINT_AND_HEADER, header = "X-User-Id", limit = 5, duration = 60)
    @PostMapping("/transfer")
    public Map<String, String> transfer() {
        return Map.of("status", "ok");
    }
}*/
