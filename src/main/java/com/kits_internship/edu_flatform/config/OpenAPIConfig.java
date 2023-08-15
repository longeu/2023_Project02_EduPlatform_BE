package com.kits_internship.edu_flatform.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

  @Bean
  public OpenAPI myOpenAPI() {
    Contact contact = new Contact();
    contact.setEmail("longeu100@gmail.com");
    contact.setName("Edu-Platform");

    Info info = new Info()
        .title("Tutorial Management API")
        .version("1.0")
        .contact(contact)
        .description("This API exposes endpoints to manage tutorials.");

    return new OpenAPI().info(info);
  }
}