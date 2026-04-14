package com.projects.eventticket.eventticket.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.projects.eventticket.eventticket.domain.entity.Event;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static java.time.Duration.ofDays;
import static org.springframework.data.redis.cache.RedisCacheConfiguration.defaultCacheConfig;
import static org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair.fromSerializer;

@EnableCaching
@Configuration
public class RedisCacheConfig {
    @Bean
    public RedisCacheConfiguration cacheConfiguration() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        objectMapper.activateDefaultTyping(
                objectMapper.getPolymorphicTypeValidator(),
                ObjectMapper.DefaultTyping.NON_FINAL,
                PROPERTY
        );

        return defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(60))
                .disableCachingNullValues()
                .serializeValuesWith(
                        fromSerializer(
                                new GenericJackson2JsonRedisSerializer(objectMapper)
                        )
                );
    }


}
