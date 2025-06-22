package me.danhthanhf.distant_class.service;

import me.danhthanhf.distant_class.common.constant.AppConstants;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

@Component
public class RedisService {
    private final RedisTemplate<String, Object> genericTemplate;

    // Using ValueOperations to handle Redis type of key is String and value is Object
    private final ValueOperations<String, Object> valueOps;

    public RedisService(@Qualifier(AppConstants.REDIS_TEMPLATE_GENERIC) RedisTemplate<String, Object> genericTemplate) {
        this.genericTemplate = genericTemplate;
        this.valueOps = genericTemplate.opsForValue();
    }


    public void saveTokenToUser(String userId, String token) {
        save(userId, token);
    }

    public String getTokenFromUser(String userId) {
        return get(userId, String.class);
    }

    // base methods for Redis operations
    public <T> void save(String key, T value) {
        valueOps.set(key, value);
    }

    public <T> T get(String key, Class<T> type) {
        return type.cast(valueOps.get(key));
    }

    public void deleteTokenFromUser(String userId) {
        genericTemplate.delete(userId);
    }
}
