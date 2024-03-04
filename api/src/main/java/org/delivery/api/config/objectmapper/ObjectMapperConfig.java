package org.delivery.api.config.objectmapper;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperConfig {

    @Bean // 직접 ObjectMapper 만들어서 넣어주기 (이거 안만들면 Spring이 default 설정으로 알아서 만들어줌)
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.registerModule(new Jdk8Module()); // jdk 8 버전 이후 클래스 처리를 위한 모듈들

        objectMapper.registerModule(new JavaTimeModule()); // local date 시리즈

        // Deserialization할 때 모르는 json field에 대해서는 그 값을 무시하고 나머지 파싱
        // ex) AccountMeResponse에서 name, email, registeredAt 값 이외에 모르는 값이 같이 오면 이를 무시할 건지 에러를 터뜨릴 건지 설정
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // Serialization할 때, 비어있는 거 설정할 때 어떻게 할지
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        // 날짜 관련 직렬화
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // snake case로 쓰겠다
        objectMapper.setPropertyNamingStrategy(new PropertyNamingStrategies.SnakeCaseStrategy());

        return objectMapper;

    }
}
