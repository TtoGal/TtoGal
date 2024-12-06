package com.ttogal.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
            .info(new Info()
                    .title("springdoc-openapi")
                    .version("1.0")
                    .description("Ttogal swagger-ui 화면입니다")
            )
            .components(new Components()
                    .addSecuritySchemes("bearer-jwt",
                            new SecurityScheme()
                                    .type(SecurityScheme.Type.HTTP)
                                    .scheme("bearer")
                                    .bearerFormat("JWT")))
            .addSecurityItem(new SecurityRequirement().addList("bearer-jwt"));

  }

  @Bean
  public GroupedOpenApi getAllAPI() {
    return GroupedOpenApi.builder()
            .group("all")
            .pathsToMatch("/api/v1/**")
            .build();
  }

  @Bean
  public GroupedOpenApi getUserApi(){
    return GroupedOpenApi.builder()
            .group("user")
            .pathsToMatch("/api/v1/users/**")
            .build();
  }

  @Bean
  public GroupedOpenApi getEmailApi(){
    return GroupedOpenApi.builder()
            .group("email")
            .pathsToMatch("/api/v1/email/**")
            .build();
  }
}
