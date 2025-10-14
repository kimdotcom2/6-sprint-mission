package com.sprint.mission.discodeit.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI customOpenAPI() {

    final String securitySchemeName = "noop";

    return new OpenAPI()
        .components(new Components()
            .addSecuritySchemes(securitySchemeName,
                new SecurityScheme()
                    .name(securitySchemeName)
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("none")))
        .security(List.of(new SecurityRequirement()))
        .servers(List.of(
            new Server().url("http://localhost:8080").description("Local Server")
        ))
        .info(new Info()
            .title("DisCodeIt API Documentation")
            .description("DisCodeIt 서비스의 API 문서입니다.\n\n" +
                "## 접근 정보\n" +
                "- **Swagger UI**: [/swagger-ui.html](http://localhost:8080/swagger-ui.html)\n" +
                "- **OpenAPI JSON**: [/v3/api-docs](http://localhost:8080/v3/api-docs)\n\n")
            .version("1.0.0"));
  }
}
