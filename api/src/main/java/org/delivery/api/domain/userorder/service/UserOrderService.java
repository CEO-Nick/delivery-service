package org.delivery.api.domain.userorder.service;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.db.userorder.UserOrderEntity;
import org.delivery.db.userorder.UserOrderRepository;
import org.delivery.db.userorder.enums.UserOrderStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.delivery.db.userorder.enums.UserOrderStatus.*;

@Service
@RequiredArgsConstructor
public class UserOrderService {

    private final UserOrderRepository userOrderRepository;

    public UserOrderEntity getUserOrderWithoutStatusWithThrow(
            Long id,
            Long userId
    ) {
        return userOrderRepository.findAllByIdAndUserId(id, userId)
                .orElseThrow(()-> new ApiException(ErrorCode.NULL_POINT));
    }


    // 특정 사용자의 특정 주문 가져오기
    public UserOrderEntity getUserOrderWithThrow(
            Long id,
            Long userId
    ) {
        return userOrderRepository.findAllByIdAndUserIdAndStatus(id, userId, REGISTERED)
                .orElseThrow(()-> new ApiException(ErrorCode.NULL_POINT));
    }

    // 특정 사용자의 모든 주문 내역 가져오기
    public List<UserOrderEntity> getUserOrderList(
            Long userId
    ) {
    return userOrderRepository.findAllByUserIdAndStatusOrderByIdDesc(userId, REGISTERED);
    }


    public List<UserOrderEntity> getUserOrderList(
            Long userId,
            List<UserOrderStatus> statusList
    ) {
        return userOrderRepository.findAllByUserIdAndStatusInOrderByIdDesc(userId, statusList);
    }

    // 현재 진행 중인 내역
    public List<UserOrderEntity> current(Long userId) {

//        List<UserOrderStatus> statusList = new ArrayList<>(4);
//        statusList.add(UserOrderStatus.ORDER);
//        statusList.add(UserOrderStatus.ACCEPT);
//        statusList.add(UserOrderStatus.COOKING);
//        statusList.add(UserOrderStatus.DELIVERY);
//
//        return getUserOrderList(userId, statusList);

        return getUserOrderList(
                userId,
                List.of(
                        ORDER,
                        ACCEPT,
                        COOKING,
                        DELIVERY
                ));
    }

    // 과거 주문 내역
    public List<UserOrderEntity> history(Long userId) {
        return getUserOrderList(
                userId,
                List.of(RECEIVE));
    }


    // 주문하기 (create)
    public UserOrderEntity order(
            UserOrderEntity userOrderEntity
    ) {
        return Optional.ofNullable(userOrderEntity)
                .map(it -> {
                    it.setStatus(ORDER);
                    it.setOrderedAt(LocalDateTime.now());
                    return userOrderRepository.save(it);
                })
                .orElseThrow(()-> new ApiException(ErrorCode.NULL_POINT));
    }

    // 상태 변경 메서드롤 통해 동일한 행위 메서드로 묶기
    public UserOrderEntity setStatus(
            UserOrderEntity userOrderEntity,
            UserOrderStatus status
    ) {
        userOrderEntity.setStatus(status);
        return userOrderRepository.save(userOrderEntity);
    }

    // 주문 확인
    public UserOrderEntity accept(UserOrderEntity userOrderEntity) {
        userOrderEntity.setAcceptedAt(LocalDateTime.now());
        return setStatus(userOrderEntity, ACCEPT);
    }

    // 조리 시작
    public UserOrderEntity cooking(UserOrderEntity userOrderEntity) {
        userOrderEntity.setCookingStartedAt(LocalDateTime.now());
        return setStatus(userOrderEntity, COOKING);
    }

    // 배달 시작
    public UserOrderEntity delivery(UserOrderEntity userOrderEntity) {
        userOrderEntity.setDeliveryStartedAt(LocalDateTime.now());
        return setStatus(userOrderEntity, DELIVERY);
    }

    // 배달 완료
    public UserOrderEntity receive(UserOrderEntity userOrderEntity) {
        userOrderEntity.setReceivedAt(LocalDateTime.now());
        return setStatus(userOrderEntity, RECEIVE);
    }
}
