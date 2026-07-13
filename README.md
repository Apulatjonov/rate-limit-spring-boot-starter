# README.md

# Rate Limit Spring Boot Starter

A lightweight, configurable, production-ready **Spring Boot rate limiting starter** with support for:

[![Maven Central](https://img.shields.io/maven-central/v/io.github.apulatjonov/rate-limit-spring-boot-starter)](https://central.sonatype.com/artifact/io.github.apulatjonov/rate-limit-spring-boot-starter)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Java](https://img.shields.io/badge/Java-17%2B-orange.svg)](https://www.oracle.com/java/)

* In-Memory storage
* Redis distributed storage
* Automatic backend selection
* Annotation-based API limits
* Custom key strategies
* Spring Boot Auto Configuration

---

## Features

### Storage Backends

Choose one:

* `memory` → local in-memory limiter
* `redis` → distributed limiter for multi-instance apps
* `auto` → uses Redis if available, otherwise memory

---

### Annotation Driven

```java
@RateLimit(limit = 5, duration = 60)
@PostMapping("/transfer")
public String transfer() {
    return "ok";
}
```

---

### Response Headers

Automatically returns:

```text
X-RateLimit-Limit
X-RateLimit-Remaining
Retry-After
```

---

### HTTP 429 Protection

Returns:

```json
{
  "code": "RATE_LIMIT_EXCEEDED",
  "message": "Too many requests"
}
```

---

## Installation

### Maven

```xml
<dependency>
    <groupId>io.github.apulatjonov</groupId>
    <artifactId>rate-limit-spring-boot-starter</artifactId>
    <version>1.0.0</version>
</dependency>
```

---

## Configuration

### application.yml

```yaml
rate-limit:
  storage: auto
```

### Available Modes

#### Memory

```yaml
rate-limit:
  storage: memory
```

#### Redis

```yaml
rate-limit:
  storage: redis
```

#### Auto

```yaml
rate-limit:
  storage: auto
```

---

## Redis Configuration

```yaml
spring:
  data:
    redis:
      host: localhost
      port: 6379
```

---

## Usage

### Basic Example

```java
@RestController
public class PaymentController {

    @RateLimit(limit = 3, duration = 60)
    @PostMapping("/payment")
    public String pay() {
        return "success";
    }
}
```

---

### Endpoint + Header Strategy

Example using:

```text
X-User-Id
```

Separate limits per user per endpoint.

```java
@RateLimit(
    strategy = KeyStrategy.ENDPOINT_AND_HEADER,
    header = "X-User-Id",
    limit = 5,
    duration = 60
)
@PostMapping("/transfer")
public String transfer() {
    return "ok";
}
```

---

## How It Works

Example key generation:

```text
POST:/transfer:145
POST:/transfer:999
```

Each user gets separate counters.

---

## Use Cases

* Login brute-force prevention
* OTP spam prevention
* Payment throttling
* Public API quotas
* Multi-user mobile apps
* FinTech / Banking systems

---

## Example Response Headers

```text
X-RateLimit-Limit: 5
X-RateLimit-Remaining: 2
Retry-After: 43
```

---

## Roadmap

* Sliding Window algorithm
* Token Bucket support
* Metrics / Prometheus
* `@OtpProtection`
* `@LoginProtection`
* Dashboard UI
* Dynamic rules from DB

---

## Requirements

* Java 17+
* Spring Boot 3+

---

## Contributing

Pull requests and ideas are welcome.

---

## License

MIT License

---

## Author

**Abdulaziz Pulatjonov**

GitHub: https://github.com/apulatjonov
