package io.github.guardjo.ticketmanager.batch.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("이용권 관리 배치 서비스")
                .version("v1.0.1")
                .description("이용권 관리 서비스 내 배치 프로세스 수행 서버");

        return new OpenAPI()
                .components(new Components())
                .info(info);
    }
}
