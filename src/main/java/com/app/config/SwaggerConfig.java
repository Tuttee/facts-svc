package com.app.config;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {

        Info applicationInfo = new Info()
                .title("Facts REST API")
                .description("REST API designed to provide Cacti Facts.")
                .version("1.0")
                .contact(new Contact()
                        .name("Tuttee")
                        .email("tuttee@test.com")
                        .url("http://localhost:8081"))
                .license(new License()
                        .name("MIT License")
                        .url("http://opensource.org/licenses/MIT"));

        return new OpenAPI().info(applicationInfo);
    }

}
