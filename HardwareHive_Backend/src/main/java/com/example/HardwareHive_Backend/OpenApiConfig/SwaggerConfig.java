package com.example.HardwareHive_Backend.OpenApiConfig;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@OpenAPIDefinition(
        info = @Info(
                title = "HardwareHive Service",
                version = "1.0",
                description = "An api used for an eshop. Handles the items, users and orders"
        ),
        security = {
                @SecurityRequirement(name = "bearerAuth")
        }
)
public class SwaggerConfig {
}