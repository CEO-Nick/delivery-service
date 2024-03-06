package org.delivery.api.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode implements ErrorCodeIfs{

    // 실제 Http status 코드와 우리 errorCode가 일치할 수도, 다를 수도 있음
    OK(200, 200, "성공"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), 400, "잘못된 요청"),
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), 500, "서버 에러"),
    NULL_POINT(HttpStatus.INTERNAL_SERVER_ERROR.value(), 512, "Null point")

    ;

    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String description;
    
    //@Getter로 퉁쳐짐
//    @Override
//    public Integer getHttpStatusCode() {
//        return httpStatusCode;
//    }
//
//    @Override
//    public Integer getErrorCode() {
//        return errorCode;
//    }
//
//    @Override
//    public String getDescription() {
//        return description;
//    }
    
    
}
