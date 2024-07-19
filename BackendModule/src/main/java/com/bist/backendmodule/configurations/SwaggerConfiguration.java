package com.bist.backendmodule.configurations;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger and OpenAPI configuration.
 */
@Configuration
public class SwaggerConfiguration {

    /**
     * Custom OpenAPI configuration.
     *
     * @param description The application description.
     * @param version     The application version.
     * @return Custom OpenAPI instance.
     */
    @Bean
    public OpenAPI customOpenAPI(@Value("${application-description}") String description,
                                 @Value("${application-version}") String version) {
        return new OpenAPI()
                .info(new Info()
                        .title("Car Management System API")
                        .version(version)
                        .description(description)
                        .license(new License().name("Car Management System API License")));
    }

    /**
     * Grouped API configuration.
     * Includes car, brand, image, group, permission, role, user controllers
     *
     * @return GroupedOpenApi instance.
     */
    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("all-apis")
                .pathsToMatch("/car/**", "/brand/**", "/image/**", "/group/**", "/permission/**", "/role/**", "/user/**")
                .build();
    }
}
