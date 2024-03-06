package org.delivery.api.exceptionhandler;

import lombok.extern.slf4j.Slf4j;
import org.delivery.api.common.api.Api;
import org.delivery.api.common.exception.ApiException;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@Order(value = Integer.MIN_VALUE) // 최우선 처리
public class ApiExceptionHandler {

    // ApiException 클래스로 발생하는 모든 예외를 여기서 다 캐치!
    @ExceptionHandler(value = ApiException.class) 
    public ResponseEntity<Api<Object>> apiException(ApiException apiException) { // apiException은 SpringBoot가 넣어줌
        log.error("", apiException); // stacktrace 찍기(apiException은 RuntimeException을 상속받았기에 가능)

        var errorCode = apiException.getErrorCodeIfs();

        return ResponseEntity
                .status(errorCode.getHttpStatusCode())
                .body(
                        Api.ERROR(errorCode, apiException.getErrorDescription())
                        // body에 Api.ERROR 넣어주면 아래와 같이 만들어준다
                        /*
                        "result": {
                            "result_code": 500,
                            "result_message": "서버 에러",
                            "result_description": "에러 발생"
                        }, */

                );

    }
}
