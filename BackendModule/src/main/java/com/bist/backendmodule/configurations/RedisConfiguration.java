package com.bist.backendmodule.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * RedisConfiguration class configures RedisTemplate for the application.
 * It sets up the connection factory and specifies the key and value serializers.
 */
@Configuration
public class RedisConfiguration {

    /**
     * Configures a RedisTemplate with string serialization for both keys and values.
     *
     * @param redisConnectionFactory the Redis connection factory to use
     * @return a configured RedisTemplate instance
     */
    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        // Key serializer
        template.setKeySerializer(new StringRedisSerializer());

        // Value serializer
        template.setValueSerializer(new StringRedisSerializer());

        return template;
    }
}
