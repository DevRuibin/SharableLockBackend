package org.example.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class PasswordService {
    private final RedisTemplate<String, String> redisTemplate;

    // geberate a 4 digit code
    private String generateCode(){
        Random random = new Random();
        return String.format("%04d", random.nextInt(10000));
    }

    private void storeCode(String userId, String code){
        redisTemplate.opsForValue().set(userId, code, 5, TimeUnit.MINUTES);
    }

    public String getCode(String userId){
        return redisTemplate.opsForValue().get(userId);
    }

    public String requestCode(String userId) {
        String code = generateCode();
        storeCode(userId, code);
        return code;
    }

}
