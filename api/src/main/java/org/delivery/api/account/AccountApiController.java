package org.delivery.api.account;

import lombok.RequiredArgsConstructor;
import org.delivery.api.account.model.AccountMeResponse;
import org.delivery.api.common.api.Api;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.db.account.AccountEntity;
import org.delivery.db.account.AccountRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountApiController {

    private final AccountRepository accountRepository;
    
    // API는 성공 OR 실패
    // 실패는 ExceptionHandler에서 처리
    // Controller에서는 무조건 성공에 대한 처리
    @GetMapping("/me")
    public Api<AccountMeResponse> save() {

        AccountMeResponse response = AccountMeResponse.builder()
                .name("조익현")
                .email("andantej99@naver.com")
                .registeredAt(LocalDateTime.now())
                .build();

        String str = "안녕하세요";
        int age = 0;
        try {
            Integer.parseInt(str); // -> 런타임 예외 발생함
        } catch (Exception e) {
            throw new ApiException(ErrorCode.SERVER_ERROR, e, "사용자 me 호출시 에러 발생");
        }
        return Api.OK(response); //API는 항상 성공 처리

    }

}
