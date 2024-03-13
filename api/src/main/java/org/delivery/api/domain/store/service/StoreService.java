package org.delivery.api.domain.store.service;

import ch.qos.logback.core.spi.ErrorCodes;
import lombok.RequiredArgsConstructor;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.db.store.StoreEntity;
import org.delivery.db.store.StoreRepository;
import org.delivery.db.store.enums.StoreCategory;
import org.delivery.db.store.enums.StoreStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    // 유효한 store 가져오기
    public StoreEntity getStoreWithThrow(Long id) {
        Optional<StoreEntity> entity = storeRepository.findFirstByIdAndStatusOrderByIdDesc(id, StoreStatus.REGISTERED);
        return entity.orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }

    // store 등록
    @Transactional
    public StoreEntity register(StoreEntity store) {
        // store 는 null 일 수 있음
        return Optional.ofNullable(store)
                .map(it -> {
                    it.setStar(0);
                    it.setStatus(StoreStatus.REGISTERED);

                    // TODO 등록일시 추가하기
                    return storeRepository.save(it);
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }


    // category로 store 검색
    public List<StoreEntity> searchByCategory(StoreCategory storeCategory) {
        List<StoreEntity> list = storeRepository.findAllByStatusAndCategoryOrderByStarDesc(
                StoreStatus.REGISTERED,
                storeCategory);
        return list;
    }

    // 전체 store 검색
    public List<StoreEntity> searchStore() {
        List<StoreEntity> allList = storeRepository.findAllByStatusOrderByIdDesc(StoreStatus.REGISTERED);
        return allList;
    }
}
