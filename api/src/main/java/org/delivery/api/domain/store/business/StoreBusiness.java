package org.delivery.api.domain.store.business;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotaion.Business;
import org.delivery.api.domain.store.controller.model.StoreRegisterRequest;
import org.delivery.api.domain.store.controller.model.StoreResponse;
import org.delivery.api.domain.store.converter.StoreConverter;
import org.delivery.api.domain.store.service.StoreService;
import org.delivery.db.store.StoreEntity;
import org.delivery.db.store.enums.StoreCategory;

import java.util.List;
import java.util.stream.Collectors;

@Business
@RequiredArgsConstructor
public class StoreBusiness {

    private final StoreService storeService;
    private final StoreConverter storeConverter;

    public StoreResponse register(StoreRegisterRequest request) {
        StoreEntity entity = storeConverter.toEntity(request);
        StoreEntity newEntity = storeService.register(entity);
        StoreResponse response = storeConverter.toResponse(newEntity);

        return response;
    }

    public List<StoreResponse> searchCategory(StoreCategory category) {
        List<StoreEntity> entityList = storeService.searchByCategory(category);
        List<StoreResponse> responseList = entityList.stream()
                .map(storeConverter::toResponse)
                .collect(Collectors.toList());

        return responseList;
    }
}
