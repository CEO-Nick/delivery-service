package org.delivery.api.config.swagger;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.core.jackson.ModelResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    // Spring이 ObjectMapper라는 bean을 찾아서 여기에 넣어준다
    // 어떤 ObjectMapper가 들어오냐? -> ObjectMapperConfig.java에서 만든 OM이 들어간다
    @Bean
    public ModelResolver modelResolver(ObjectMapper objectMapper) {
        return new ModelResolver(objectMapper);
    }
}
