package org.delivery.api.domain.userorder.business;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotaion.Business;
import org.delivery.api.domain.store.converter.StoreConverter;
import org.delivery.api.domain.store.service.StoreService;
import org.delivery.api.domain.storemenu.converter.StoreMenuConverter;
import org.delivery.api.domain.storemenu.service.StoreMenuService;
import org.delivery.api.domain.user.model.User;
import org.delivery.api.domain.userorder.controller.model.UserOrderDetailResponse;
import org.delivery.api.domain.userorder.controller.model.UserOrderRequest;
import org.delivery.api.domain.userorder.controller.model.UserOrderResponse;
import org.delivery.api.domain.userorder.converter.UserOrderConverter;
import org.delivery.api.domain.userorder.service.UserOrderService;
import org.delivery.api.domain.userordermenu.converter.UserOrderMenuConverter;
import org.delivery.api.domain.userordermenu.service.UserOrderMenuService;
import org.delivery.db.store.StoreEntity;
import org.delivery.db.storemenu.StoreMenuEntity;
import org.delivery.db.userorder.UserOrderEntity;
import org.delivery.db.userordermenu.UserOrderMenuEntity;

import java.util.List;
import java.util.stream.Collectors;

@Business
@RequiredArgsConstructor
public class UserOrderBusiness {
    private final UserOrderService userOrderService;
    private final StoreMenuService storeMenuService;
    private final UserOrderConverter userOrderConverter;
    private final UserOrderMenuConverter userOrderMenuConverter;
    private final UserOrderMenuService userOrderMenuService;
    private final StoreService storeService;
    private final StoreMenuConverter storeMenuConverter;
    private final StoreConverter storeConverter;

    // 1. 사용자, 메뉴 id
    // 2. UserOrder 생성
    // 3. UserOrderMenu 생성
    // 4. 응답 생성
    public UserOrderResponse userOrder(User user, UserOrderRequest body) {
        List<StoreMenuEntity> storeMenuEntityList = body.getStoreMenuIdList()
                .stream()
                .map(storeMenuService::getStoreMenuWithThrow)
                .collect(Collectors.toList());

        UserOrderEntity userOrderEntity = userOrderConverter.toEntity(user, storeMenuEntityList);

        // 주문
        UserOrderEntity newUserOrderEntity = userOrderService.order(userOrderEntity);

        // 매핑(UserOrderMenu 생성)
        List<UserOrderMenuEntity> userOrderMenuEntityList = storeMenuEntityList.stream()
                .map(it -> {
                    // menu + user order
                    UserOrderMenuEntity userOrderMenuEntity = userOrderMenuConverter.toEntity(
                            newUserOrderEntity,
                            it);
                    return userOrderMenuEntity;
                })
                .collect(Collectors.toList());

        // 주문 내역 기록 남기기
        userOrderMenuEntityList.forEach(it -> {
            userOrderMenuService.order(it);
        });

        // response
        return userOrderConverter.toResponse(newUserOrderEntity);
    }

    public List<UserOrderDetailResponse> current(User user) {
        // 현재 유저가 주문 목록 가져오기
        List<UserOrderEntity> userOrderEntityList = userOrderService.current(user.getId());
        
        // 각 주문 건에 대한 처리
        List<UserOrderDetailResponse> userOrderDetailResponseList = userOrderEntityList.stream()
                .map(it -> {

                    // 각 주문에 대한 메뉴들 뽑기
                    List<UserOrderMenuEntity> userOrderMenuEntityList = userOrderMenuService.getUserOrderMenu(it.getId());
                    List<StoreMenuEntity> storeMenuEntityList = userOrderMenuEntityList.stream()
                            .map(userOrderMenuEntity -> {
                                StoreMenuEntity storeMenuEntity = storeMenuService.getStoreMenuWithThrow(userOrderMenuEntity.getStoreMenuId());
                                return storeMenuEntity;
                            })
                            .collect(Collectors.toList());

                    // 사용자가 주문한 스토어 TODO 리팩토링 필요 : get()에서 NPE 발생 가능
                    StoreEntity storeEntity =  storeService.getStoreWithThrow(storeMenuEntityList.stream().findFirst().get().getStoreId());
                    return UserOrderDetailResponse.builder()
                            .userOrderResponse(userOrderConverter.toResponse(it))
                            .storeResponse(storeConverter.toResponse(storeEntity))
                            .storeMenuResponseList(storeMenuConverter.toResponse(storeMenuEntityList))
                            .build();

                }).collect(Collectors.toList());

        return userOrderDetailResponseList;
    }

    public List<UserOrderDetailResponse> history(User user) {
        // 현재 유저의 과거 주문 내역 (history) 가져오기
        List<UserOrderEntity> userOrderEntityList = userOrderService.history(user.getId());

        // 각 주문 건에 대한 처리
        List<UserOrderDetailResponse> userOrderDetailResponseList = userOrderEntityList.stream()
                .map(it -> {

                    // 각 주문에 대한 메뉴들 뽑기
                    List<UserOrderMenuEntity> userOrderMenuEntityList = userOrderMenuService.getUserOrderMenu(it.getId());
                    List<StoreMenuEntity> storeMenuEntityList = userOrderMenuEntityList.stream()
                            .map(userOrderMenuEntity -> {
                                StoreMenuEntity storeMenuEntity = storeMenuService.getStoreMenuWithThrow(userOrderMenuEntity.getStoreMenuId());
                                return storeMenuEntity;
                            })
                            .collect(Collectors.toList());

                    // 사용자가 주문한 스토어 TODO 리팩토링 필요 : get()에서 NPE 발생 가능
                    StoreEntity storeEntity =  storeService.getStoreWithThrow(storeMenuEntityList.stream().findFirst().get().getStoreId());

                    return UserOrderDetailResponse.builder()
                            .userOrderResponse(userOrderConverter.toResponse(it))
                            .storeResponse(storeConverter.toResponse(storeEntity))
                            .storeMenuResponseList(storeMenuConverter.toResponse(storeMenuEntityList))
                            .build();

                }).collect(Collectors.toList());

        return userOrderDetailResponseList;
    }


    /**
     * 특정 유저가 특정 주문에 대한 내역을 보고싶을 때
     * @param user
     * @param orderId
     * @return
     */
    public UserOrderDetailResponse read(User user, Long orderId) {
        // 사용자의 특정 주문 내역 가져오기
        UserOrderEntity userOrderEntity = userOrderService.getUserOrderWithoutStatusWithThrow(orderId, user.getId());

        // 사용자가 주문한 메뉴
        List<UserOrderMenuEntity> userOrderMenuEntityList = userOrderMenuService.getUserOrderMenu(userOrderEntity.getId());
        List<StoreMenuEntity> storeMenuEntityList = userOrderMenuEntityList.stream()
                .map(userOrderMenuEntity -> {
                    StoreMenuEntity storeMenuEntity = storeMenuService.getStoreMenuWithThrow(userOrderMenuEntity.getStoreMenuId());
                    return storeMenuEntity;
                })
                .collect(Collectors.toList());

        // 사용자가 주문한 스토어
        StoreEntity storeEntity =  storeService.getStoreWithThrow(storeMenuEntityList.stream().findFirst().get().getStoreId());

        return UserOrderDetailResponse.builder()
                .userOrderResponse(userOrderConverter.toResponse(userOrderEntity))
                .storeResponse(storeConverter.toResponse(storeEntity))
                .storeMenuResponseList(storeMenuConverter.toResponse(storeMenuEntityList))
                .build();
    }
}
