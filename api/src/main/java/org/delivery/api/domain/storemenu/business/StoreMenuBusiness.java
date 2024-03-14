package org.delivery.api.domain.storemenu.business;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotaion.Business;
import org.delivery.api.domain.storemenu.controller.model.StoreMenuRegisterRequest;
import org.delivery.api.domain.storemenu.controller.model.StoreMenuResponse;
import org.delivery.api.domain.storemenu.converter.StoreMenuConverter;
import org.delivery.api.domain.storemenu.service.StoreMenuService;
import org.delivery.db.storemenu.StoreMenuEntity;

import java.util.List;
import java.util.stream.Collectors;

@Business
@RequiredArgsConstructor
public class StoreMenuBusiness {

    private final StoreMenuService storeMenuService;
    private final StoreMenuConverter storeMenuConverter;

    public StoreMenuResponse register(
            StoreMenuRegisterRequest request
    ) {
        StoreMenuEntity entity = storeMenuConverter.toEntity(request); // toEntity에서 request null check함
        StoreMenuEntity storedEntity = storeMenuService.register(entity);
        StoreMenuResponse response = storeMenuConverter.toResponse(storedEntity);
        return response;
    }

    public List<StoreMenuResponse> search(
            Long storeId
    ) {
        List<StoreMenuEntity> menuList = storeMenuService.getStoreMenuByStoreId(storeId);
        return menuList.stream()
                .map(storeMenuConverter::toResponse) // 각 StoreMenuEntity -> StoreMenuResponse로 변환
                .collect(Collectors.toList());
    }


}
