package com.jt.quick_grade.web.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(servers = {@Server(url = "/quick-grade", description = "Default Server URL"),})
@Configuration
public class OpenApiConfig {

    private static final String SECURITY_JWT_SCHEME_NAME = "bearerAuth";

    @Bean
    public OpenAPI openAPI() {
        OpenAPI openAPI = new OpenAPI();
         openAPI.addSecurityItem(createSecurityRequirement());
         openAPI.setComponents(createComponents());
        return openAPI;
    }

    private SecurityRequirement createSecurityRequirement() {
        return new SecurityRequirement().addList(SECURITY_JWT_SCHEME_NAME);
    }

    private Components createComponents() {
        return new Components()
                .addSecuritySchemes(SECURITY_JWT_SCHEME_NAME, createJwtBearerSecurity());
    }

    private SecurityScheme createJwtBearerSecurity() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");
    }
}
