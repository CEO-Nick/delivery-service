package org.delivery.db.store;

import org.delivery.db.store.enums.StoreCategory;
import org.delivery.db.store.enums.StoreStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<StoreEntity, Long> {

    // 유호한 스토어 (id와 status로)
    // select * from store where id = ? and status="REGISTERED" order by id desc limit 1
    Optional<StoreEntity> findFirstByIdAndStatusOrderByIdDesc(Long storeId, StoreStatus status);


    // 유효한 스토어 리스트
    // select * from store where status="REGISTERED" order by id desc
    List<StoreEntity> findAllByStatusOrderByIdDesc(StoreStatus status);

    // 유효한 특정 카테고리의 스토어 리스트(좋아요 높은순)
    Optional<List<StoreEntity>> findAllByStatusAndCategoryOrderByStarDesc(StoreStatus status, StoreCategory category);
}
