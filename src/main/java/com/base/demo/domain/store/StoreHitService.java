package com.base.demo.domain.store;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreHitService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final String STORE_HITS_KEY = "store:hit:";

    /**
     * 상점 조회수 증가
     * @param storeId 상점 ID
     */
    public void incrementHitCount(Long storeId) {
        String key = STORE_HITS_KEY + storeId;
        redisTemplate.opsForValue().increment(key);
    }

    /**
     * 상점 조회수 가져오기
     * @param storeId 상점 ID
     * @return 조회수
     */
    public Long getHitCount(Long storeId) {
        Object count = redisTemplate.opsForValue().get(STORE_HITS_KEY + storeId);
        return count != null ? Long.parseLong(count.toString()) : 0L;
    }
}
