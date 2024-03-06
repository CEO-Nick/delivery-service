package org.delivery.api.exceptionhandler;

import lombok.extern.slf4j.Slf4j;
import org.delivery.api.common.api.Api;
import org.delivery.api.common.error.ErrorCode;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@Order(value = Integer.MAX_VALUE) // 가장 마지막에 실행 적용 (값이 높을수록 마지막에 실행)
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Api<Object>> exception(Exception exception) {
        log.error("", exception); // stacktrace 찍기 -> 어디, 몇 번째 라인에서 에러 터졌는지 알 수 있음

        // 클라이언트한테는 stacktrace와 같은 상세 내용을 알려줄 필요 없기에 그냥 500 에러 내리기
        return ResponseEntity
                .status(500)
                .body(
                        Api.ERROR(ErrorCode.SERVER_ERROR)
                );
    }
}
