package org.delivery.api.domain.userorder.controller;


import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotaion.UserSession;
import org.delivery.api.common.api.Api;
import org.delivery.api.domain.user.model.User;
import org.delivery.api.domain.userorder.business.UserOrderBusiness;
import org.delivery.api.domain.userorder.controller.model.UserOrderDetailResponse;
import org.delivery.api.domain.userorder.controller.model.UserOrderRequest;
import org.delivery.api.domain.userorder.controller.model.UserOrderResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-order")
@RequiredArgsConstructor
public class UserOrderApiController {

    private final UserOrderBusiness userOrderBusiness;


    // 사용자 주문
    @PostMapping("")
    public Api<UserOrderResponse>  userOrder(
            @Valid @RequestBody
            Api<UserOrderRequest> request,

            @Parameter(hidden = true)
            @UserSession User user
    ) {
        UserOrderResponse response = userOrderBusiness.userOrder(
                user,
                request.getBody()
        );

        return Api.OK(response);
    }

    // 현재 진행중인 주문 건
    @GetMapping("/current")
    public Api<List<UserOrderDetailResponse>> current(
            @UserSession @Parameter(hidden = true)
            User user
    ) {
        List<UserOrderDetailResponse> response = userOrderBusiness.current(user);

        return Api.OK(response);
    }
    
    
    // 과거 주문 내역
    @GetMapping("/history")
    public Api<List<UserOrderDetailResponse>> history(
            @UserSession @Parameter(hidden = true)
            User user
    ) {
        List<UserOrderDetailResponse> response = userOrderBusiness.history(user);

        return Api.OK(response);
    }
    
    
    // 주문 1건에 대한 내역
    @GetMapping("id/{orderId}")
    public Api<UserOrderDetailResponse> read(
            @UserSession @Parameter(hidden = true) // swagger에서 안보여지도록
            User user,
            @PathVariable Long orderId
    ) {
        UserOrderDetailResponse response = userOrderBusiness.read(user, orderId);

        return Api.OK(response);
    }
}
