package com.base.demo;

import com.base.demo.api.RedisMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.LocalDateTime;

@SpringBootTest
public class SerializerTest {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    ObjectMapper objectMapper;

    static String key = "MESSAGE::";

    /**
     * StringRedisSerializer 방식은 객체를 json 형태로 변환하여 저장하는 방식
     * <p> - 객체를 objectMapper를 통해 JSON 문자열로 변환 후 Redis에 저장
     * <p> - Redis에서 JSON 문자열을 가져와 objectMapper로 다시 객체로 변환
     */
    @DisplayName("DTO 직렬화 테스트")
    @Test
    public void DtoSerializerTest() throws JsonProcessingException {
        // Given
        var message = new RedisMessage(
                1L,
                1L,
                1L,
                "test message",
                LocalDateTime.now()
        );

        String redisKey = key + message.getId();

        // 객체를 JSON 문자열로 변환 후 저장
        var msgJson = objectMapper.writeValueAsString(message);
        redisTemplate.opsForHash().put("MESSAGE", redisKey, msgJson);

        // Redis에서 객체 조회 후 JSON 문자열을 객체로 변환
        Object retrievedValue = redisTemplate.opsForHash().get("MESSAGE", redisKey);
        var result = objectMapper.readValue((String) retrievedValue, RedisMessage.class);

        // 결과 출력
        System.out.println("Result Value ::: " + result.toString());
    }
}
