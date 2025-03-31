package com.base.demo.domain.store;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StoreHitServiceTest {

    private final String STORE_HITS_KEY = "store:hit:";

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

    @DisplayName("parseKey 테스트")
    @Test
    void parseKey() {
        // Given
        String key = "store:hit:1:2023-10-01";

        // When
        Map<String, String> parsedKey = parseKey(key);

        // Then
        assertEquals("1", parsedKey.get("storeId"));
        assertEquals("2023-10-01", parsedKey.get("date"));
    }

    public Map<String, String> parseKey(String key) {
        int lastColonIndex = key.lastIndexOf(":");

        // Key의 길이와 마지막 콜론 인덱스를 이용해 storeId와 date를 추출
        String storeId = key.substring(STORE_HITS_KEY.length(), lastColonIndex);
        String date = key.substring(lastColonIndex + 1);

        return Map.of(
                "storeId", storeId,
                "date", date
        );
    }
}