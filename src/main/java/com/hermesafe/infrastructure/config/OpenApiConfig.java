package com.hermesafe.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Hermesafe Logistics & Distribution API")
                        .version("1.0.0")
                        .description("Secure and documented REST API contracts for order processing, inventory management, shipping rate calculation, and route optimization.")
                        .contact(new Contact()
                                .name("Hermesafe Engineering Team")
                                .email("engineering@hermesafe.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")));
    }
}
