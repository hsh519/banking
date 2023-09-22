package com.example.banking.security.jwt;


import com.example.banking.security.redis.RefreshToken;
import com.example.banking.repository.MemberRepository;
import com.example.banking.security.redis.RedisRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class TokenProvider {

    private final Key key;
    private final MemberRepository memberRepository;
    private final RedisRepository redisRepository;

    public TokenProvider(
            @Value("${jwt.secret}") String secretKey,
            MemberRepository memberRepository,
            RedisRepository redisRepository) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.memberRepository = memberRepository;
        this.redisRepository = redisRepository;
    }

    public TokenInfo generateToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream().
                map(GrantedAuthority::getAuthority).
                collect(Collectors.joining(","));

        long now = new Date().getTime();
        Date expiredTime = new Date(now + 30 * 60 * 1000);

        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim("auth", authorities)
                .setExpiration(expiredTime)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        String refreshToken = Jwts.builder()
                .setExpiration(expiredTime)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        RefreshToken refreshTokenObj = new RefreshToken(authentication.getName(), refreshToken);
        redisRepository.save(refreshTokenObj);

        return TokenInfo.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
