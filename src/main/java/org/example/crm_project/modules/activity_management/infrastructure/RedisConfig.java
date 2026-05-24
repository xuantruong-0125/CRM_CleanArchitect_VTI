package org.example.crm_project.modules.activity_management.infrastructure;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;

@Configuration
@EnableCaching
public class RedisConfig {

    // ==========================================
    // CẤU HÌNH 1: Sử dụng bộ nhớ RAM của Java (JVM) để làm cache.
    // KHÔNG cần chạy Redis Server. Phù hợp phát triển local/offline.
    // ==========================================
    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("activities", "activity_user_names");
    }

    // ==========================================
    // CẤU HÌNH 2: Sử dụng Redis Server để làm cache (Môi trường Staging/Production).
    // Để sử dụng: Comment Cấu hình 1 lại và mở comment Cấu hình 2 bên dưới.
    // ==========================================
    /*
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory) {
        // Cấu hình ObjectMapper để xử lý được Java 8 Date/Time (LocalDateTime)
        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());

        // 1. Dạy cho Jackson biết cách đọc cấu trúc của Java Record
        om.registerModule(new com.fasterxml.jackson.module.paramnames.ParameterNamesModule());
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);

        om.activateDefaultTyping(
                LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.EVERYTHING,
                JsonTypeInfo.As.PROPERTY);

        // Dùng Serializer của Jackson thay vì mặc định của Java
        GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer(om);

        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(10)) // Cache trong 10 phút
                .disableCachingNullValues()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(serializer));

        return RedisCacheManager.builder(factory)
                .cacheDefaults(config)
                .build();
    }
    */
}