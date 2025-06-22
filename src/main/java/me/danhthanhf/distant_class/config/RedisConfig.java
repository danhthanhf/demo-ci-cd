package me.danhthanhf.distant_class.config;

import me.danhthanhf.distant_class.common.constant.AppConstants;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    @Qualifier(AppConstants.REDIS_TEMPLATE_STRING)
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(factory); // Indicate how to connect to Redis server, with host, port, in application config
        template.setKeySerializer(new StringRedisSerializer()); // Deserialize & serialize keys as strings
        template.setValueSerializer(new StringRedisSerializer()); // Deserialize & serialize keys as strings
        return template;
    }

    // ONLY SUITABLE for storing ONE specific object type per template (e.g., only User or only Product)
    // If storing multiple object types, use redisGenericTemplate instead
    // If storing different object types, deserialization may fail or cause ClassCastException
    @Bean
    @Qualifier(AppConstants.REDIS_TEMPLATE_OBJECT)
    public RedisTemplate<String, Object> redisObjectTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<Object>(Object.class)); // Deserialize & serialize values as JSON, List, Map, etc.
        return template;
    }


    // BEST PRACTICE, use for generic type (can be used for any type like String, Object, List, Map, etc.)
    // AUTOMATICALLY
    @Bean
    @Qualifier(AppConstants.REDIS_TEMPLATE_GENERIC)
    public RedisTemplate<String, Object> redisGenericTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }
}
