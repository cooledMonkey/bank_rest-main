package com.example.bankcards.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(info = @Info(
        title = "Система Управления Банковскими Картами"),
        servers = @Server(description = "Публичное API", url = "http://localhost:8080"))
@SecurityScheme(
        name = "BearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "BearerAuth",
        scheme = "bearer"
)
public class OpenApiConfig {

}
