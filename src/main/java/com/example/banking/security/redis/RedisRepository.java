package com.example.banking.security.redis;

import com.example.banking.security.redis.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RedisRepository extends CrudRepository<RefreshToken, String> {

}
