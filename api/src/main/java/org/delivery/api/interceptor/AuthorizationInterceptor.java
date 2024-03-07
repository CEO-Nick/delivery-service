package org.delivery.api.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

@Slf4j
@RequiredArgsConstructor
@Component
public class AuthorizationInterceptor implements HandlerInterceptor
{

    // request의 사전 처리 담당(인증과 같은 중요한 검증 작업을 수행)
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Object handler는 현재 요청을 처리하고 있는 핸들러(컨트롤러) 객체임
        log.info("Authorization Interceptor url : {}", request.getRequestURI());

        // WEB (특히 Chrome의 경우) GET, POST OPTIONS = pass
        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            return true;
        }

        // .js .html .png 등 resource를 요청하는 경우 = pass(인증 절차 없이 통과)
        if (handler instanceof ResourceHttpRequestHandler) {
            return true;
        }

        // TODO : header 검증(JWT 유효한지)


        return true;
    }
}
