package org.delivery.api.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotaion.UserSession;
import org.delivery.api.common.api.Api;
import org.delivery.api.domain.user.business.UserBusiness;
import org.delivery.api.domain.user.controller.model.UserResponse;
import org.delivery.api.domain.user.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Objects;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserApiController {
    private final UserBusiness userBusiness;

    @GetMapping("/me")
    public Api<UserResponse> me(@UserSession User user) {
        // requestContext는 request 1개 들어올 때마다 생성되어 필터 -> ... -> 사용자에게 응답까지 유지되는 
        // 글로벌한 저장 영역 thread local로 보면 된다
        // 이때, 저장할 때 request 단위로 저장할지, Sessoin 단위로 저장할지 선택 가능
//        RequestAttributes requestContext = Objects.requireNonNull(RequestContextHolder.getRequestAttributes());
//        Object userId = requestContext.getAttribute("userId", RequestAttributes.SCOPE_REQUEST);

        UserResponse response = userBusiness.me(user);
        return Api.OK(response);
    }
}
