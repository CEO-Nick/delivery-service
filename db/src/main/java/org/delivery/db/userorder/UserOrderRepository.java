package org.delivery.db.userorder;

import org.delivery.db.userorder.enums.UserOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserOrderRepository extends JpaRepository<UserOrderEntity, Long> {

    // 특정 유저의 모든 주문
    List<UserOrderEntity> findAllByUserIdAndStatusOrderByIdDesc(Long userId, UserOrderStatus status);

    //
    // select * from user_order where userId = ? and status in (?, ?, ...) order by id desc
    List<UserOrderEntity> findAllByUserIdAndStatusInOrderByIdDesc(Long userId, List<UserOrderStatus> status);

    // 특정 유저의 특정 주문
    // select * from user_order where id = ? and user_id = ? and status = ?
    Optional<UserOrderEntity> findAllByIdAndUserIdAndStatus(Long id, Long userId, UserOrderStatus status);


    Optional<UserOrderEntity> findAllByIdAndUserId(Long id, Long userId);
}
