package org.example.crm_project.modules.contracts.infrastructure.client;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI contractsOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("CRM - Module Hợp Đồng API")
                        .description("Module 11: Quản lý Hợp Đồng trong hệ thống CRM")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("CRM Team")
                                .email("dev@crm.com"))
                        .license(new License().name("Internal Use Only")));
    }
}
