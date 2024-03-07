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
public @interface Business {    //Business 로직과 Entity 관련 로직을 분리하기 위해 만든 애노테이션
    
    // Service -> Component 가면 value 존재 (뭔지 잘 모르겠음 ㅋㅋ)
    @AliasFor(annotation = Service.class)
    String value() default "";
}
