package com.base.demo.domain.store;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreHitBatchService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final StoreHitService storeHitService;
    private final StoreRepository storeRepository;

    private final String STORE_HITS_KEY = "store:hit:";


    // 1시간 마다 상점별 조회수 업데이트
    @Scheduled(fixedRate = 3600000)
    public void updateStoreHits() {
        Set<String> keys = redisTemplate.keys(STORE_HITS_KEY + "*");

        if (keys.isEmpty()) {
            log.info("No store hits to update.");
            return;
        }

        for (String key : keys) {
            Long storeId = Long.parseLong(key.replace(STORE_HITS_KEY, ""));
            Long hitCount = storeHitService.getHitCount(storeId);

            if (hitCount != null) {
                // 상점에 조회수 업데이트
                storeRepository.updateStoreHitCount(storeId, hitCount);
                redisTemplate.delete(key);
            }
            // DB에 조회수 업데이트 로직 추가
            log.info("Store ID: {}, Hit Count: {}", storeId, hitCount);
        }
    }
}
