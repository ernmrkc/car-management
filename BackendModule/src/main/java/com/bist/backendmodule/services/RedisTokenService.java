package com.bist.backendmodule.services;

import com.bist.backendmodule.security.models.TokenResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Service class for managing JWT tokens in Redis.
 */
@Service
public class RedisTokenService {
    private final RedisTemplate<String, String> redisTemplate;
    private final String PREFIX = "jwt:";

    /**
     * Constructs a RedisTokenService with the given Redis template.
     *
     * @param redisTemplate the Redis template for string operations
     */
    public RedisTokenService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * Saves a JWT token to Redis with a specified username as the key.
     *
     * @param tokenResponse the token response containing the username and token
     */
    public void saveToken(TokenResponse tokenResponse) {
        redisTemplate.opsForValue().set(PREFIX + tokenResponse.getUsername(), tokenResponse.getToken(), 10, TimeUnit.DAYS);
    }

    /**
     * Retrieves a JWT token from Redis by username.
     *
     * @param username the username whose token is to be retrieved
     * @return the JWT token, or null if no token is found
     */
    public String getTokenByUsername(String username) {
        return redisTemplate.opsForValue().get(PREFIX + username);
    }

    /**
     * Deletes a JWT token from Redis by username.
     *
     * @param username the username whose token is to be deleted
     */
    public void deleteTokenByUsername(String username) {
        redisTemplate.delete(PREFIX + username);
    }
}
