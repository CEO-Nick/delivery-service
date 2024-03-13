package org.delivery.api.domain.store.converter;

import org.delivery.api.common.annotaion.Converter;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.api.domain.store.controller.model.StoreRegisterRequest;
import org.delivery.api.domain.store.controller.model.StoreResponse;
import org.delivery.db.store.StoreEntity;

import java.util.Optional;

@Converter
public class StoreConverter {

    public StoreEntity toEntity(StoreRegisterRequest request) {
        return Optional.ofNullable(request)
                .map(it -> {
                    return StoreEntity.builder()
                            .name(it.getName())
                            .address(it.getAddress())
                            .category(it.getCategory())
                            .thumbnailUrl(it.getThumbnail())
                            .minimumAmount(it.getMinimumAmount())
                            .minimumDeliveryAmount(it.getMinimumDeliveryAmount())
                            .phoneNumber(it.getPhoneNumber())
                            .build();
                })
                .orElseThrow(()-> new ApiException(ErrorCode.NULL_POINT));


    }


    public StoreResponse toResponse(StoreEntity entity) {
        return Optional.ofNullable(entity)
                .map(it -> {
                    return StoreResponse.builder()
                            .id(it.getId())
                            .name(it.getName())
                            .status(it.getStatus())
                            .address(it.getAddress())
                            .category(it.getCategory())
                            .thumbnail(it.getThumbnailUrl())
                            .minimumAmount(it.getMinimumAmount())
                            .minimumDeliveryAmount(it.getMinimumDeliveryAmount())
                            .star(it.getStar())
                            .phoneNumber(it.getPhoneNumber())
                            .build();
                })
                .orElseThrow(()-> new ApiException(ErrorCode.NULL_POINT));
    }

}
