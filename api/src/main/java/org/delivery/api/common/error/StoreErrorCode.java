package org.delivery.api.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Store의 경우 3000번대 에러코드 사용
 */
@AllArgsConstructor
@Getter
public enum StoreErrorCode implements ErrorCodeIfs {


    // 가게 에러 코드는 3로 시작하는걸로 정책 결정
    STORES_NOT_FOUND(400, 3404, "해당 카테고리의 가게가 없습니다."),


    ;

    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String description;
}