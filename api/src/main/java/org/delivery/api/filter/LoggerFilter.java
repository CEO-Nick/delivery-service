package org.delivery.api.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.util.Enumeration;


@Slf4j
@Component
public class LoggerFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        ContentCachingRequestWrapper req = new ContentCachingRequestWrapper((HttpServletRequest) request);
        ContentCachingResponseWrapper res = new ContentCachingResponseWrapper((HttpServletResponse) response);

        // request 들어오는 지점(필터 적용 전)
        chain.doFilter(req, res);
        // 필터 적용 후 response 나가는 지점

        // request 정보
        Enumeration<String> headerNames = req.getHeaderNames(); // header 정보
        StringBuilder requestHeaderValues = new StringBuilder();
        headerNames.asIterator().forEachRemaining(headerKey -> {
            String headerValue = req.getHeader(headerKey);

            requestHeaderValues.append("[")
                    .append(headerKey)
                    .append(" : ")
                    .append(headerValue)
                    .append("] ");
        });


        String requestBody = new String(req.getContentAsByteArray()); // body 정보

        String uri = req.getRequestURI();
        String method = req.getMethod();
        log.info(">>>>> uri : {} , method : {} , header : {} , body : {}", uri, method, requestHeaderValues, requestBody);

        
        // response 정보
        StringBuilder resonseHeaderValues = new StringBuilder();
        res.getHeaderNames().forEach(headerKey -> {
            String headerValue = req.getHeader(headerKey);
            resonseHeaderValues.append("[")
                    .append(headerKey)
                    .append(" : ")
                    .append(headerValue)
                    .append("]");
        });

        String responseBody = new String(res.getContentAsByteArray());


        log.info("<<<<< uri : {} , method : {} , header : {} , body : {}", uri, method, resonseHeaderValues, responseBody);

        res.copyBodyToResponse(); // 이 코드 없으면 response body가 비어져서 나간다!!!!

    }
}
