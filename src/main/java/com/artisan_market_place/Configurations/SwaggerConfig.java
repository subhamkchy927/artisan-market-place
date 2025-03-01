package com.artisan_market_place.Configurations;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Market Place REST API",
                description = "Artisan Market Place API.",
                version = "1.0",
                contact = @Contact(name = "Artisan Market Place", url = "http://localhost:8080/api/session/application-details/", email = "support@artisanmarket.com")
        )
)
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("Artisan Market Place API")
                .pathsToMatch("/**")
                .build();
    }
}
