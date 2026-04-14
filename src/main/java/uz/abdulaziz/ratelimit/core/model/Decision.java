package uz.abdulaziz.ratelimit.core.model;

public record Decision(boolean allowed, long remaining, long retryAfter){}