package org.delivery.api.config.jpa;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = "org.delivery.db") // org.delivery.db 하위에 @Entity 붙은 애들을 다 스캔하겠다
@EnableJpaRepositories(basePackages = "org.delivery.db")    // org.delivery.db 하위에 @Repository 붙은 애들을 다 스캔하겠다
public class JpaConfig {
}
