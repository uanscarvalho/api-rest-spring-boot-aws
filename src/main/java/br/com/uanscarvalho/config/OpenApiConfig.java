package br.com.uanscarvalho.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("Aprendendo Spring Boot 3")
                        .version("v1")
                        .description("Tudo sobre spring boot com aws")
                        .termsOfService("http://www.uanscarvalho.com.br")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://www.uanscarvalho.com.br")));
    }
}
