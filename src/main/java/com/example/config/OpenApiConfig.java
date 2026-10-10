package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI complaintPortalOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Complaint Management Portal API")
                        .description(
                            "REST APIs for managing users, complaints, categories, agents, comments and resolutions."
                        )
                        .version("1.0.0"));
    }
}