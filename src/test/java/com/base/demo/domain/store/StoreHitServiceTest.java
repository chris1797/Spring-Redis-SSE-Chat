package com.base.demo.domain.store;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class StoreHitServiceTest {

    @Autowired
    public StoreHitService storeHitService;

    @Autowired
    public RedisTemplate<String, Object> redisTemplate;


    @DisplayName("상점 조회수 증가 테스트")
    @Test
    void incrementHitCount() {

        // Given
        Long storeId = 1L;
        Long originHitCount = storeHitService.getHitCount(storeId);

        // When
        storeHitService.incrementHitCount(storeId);

        // Then
        Long newHitCount = storeHitService.getHitCount(storeId);
        assertEquals(originHitCount + 1, newHitCount);
    }
}