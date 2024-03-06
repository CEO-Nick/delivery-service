package org.delivery.api.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum UserErrorCode implements ErrorCodeIfs{

    // 유저 에러 코드는 1로 시작하는걸로 정책 결정
    USER_NOT_FOUND(400, 1404, "사용자를 찾을 수 없음"),
    

    ;

    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String description;
}
