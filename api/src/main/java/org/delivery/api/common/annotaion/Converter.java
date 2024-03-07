package org.delivery.api.common.annotaion;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Service
public @interface Converter {    // data 변환하는 클래스(entity <-> dto) 사용할 때 이 애노테이션 쓴다
    @AliasFor(annotation = Service.class)
    String value() default "";
}
