package com.atm.management.api.domain.swaggerconfig;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI myOpenAPI() {

        Info info = new Info().title("ATM Management")
                .description("This API exposes endpoints to ATM Services.");

        return new OpenAPI().info(info);
    }
}