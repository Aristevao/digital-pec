package com.fho.digitalpec.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "DigitalPec", version = "1", description = "Integrated management for small livestock farms"))
public class SwaggerConfig {
}
