package com.example.banking.security.redis;

import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;


@AllArgsConstructor
@RedisHash(value = "refreshToken", timeToLive = 1800)
public class RefreshToken {

    @Id
    private String email;
    private String refreshToken;
}
