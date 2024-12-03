package com.ttogal.common.util;

import com.ttogal.common.exception.ExceptionCode;
import com.ttogal.common.exception.email.EmailException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@RequiredArgsConstructor
@Service
@Transactional
public class RedisUtil {

    private final StringRedisTemplate template;

    public String generateKey(String email) {
        if (email == null || email.isEmpty()) {
            throw new EmailException(ExceptionCode.NOT_FOUND_EMAIL);
        }
        return "email_verification:" + email;
    }

    public String getData(String key) {
        validateKey(key);
        if (!existData(key)) { // Key 존재 여부 확인
            throw new EmailException(ExceptionCode.NOT_FOUND_KEY);
        }
        ValueOperations<String, String> valueOperations = template.opsForValue();
        return valueOperations.get(key);
    }

    public boolean existData(String key) {
        validateKey(key);
        return Boolean.TRUE.equals(template.hasKey(key));
    }

    public void setDataExpire(String key, String value, long duration) {
        validateKey(key);
        ValueOperations<String, String> valueOperations = template.opsForValue();
        Duration expireDuration = Duration.ofSeconds(duration);
        valueOperations.set(key, value, expireDuration);
    }

    public void deleteData(String key) {
        validateKey(key);
        template.delete(key);
    }

    private void validateKey(String key) {
            if (key == null || key.isEmpty()) {
                throw new EmailException(ExceptionCode.NOT_FOUND_KEY);
            }
        }
}