package com.base.demo.domain.store;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    /**
     * 상점 조회수 업데이트
     *
     * @param storeId  상점 ID
     * @param hitCount 조회수
     */
    @Modifying
    @Query("UPDATE Store s SET s.hit = s.hit + :viewCount WHERE s.id = :storeId")
    void updateStoreHitCount(Long storeId, Long hitCount);
}
