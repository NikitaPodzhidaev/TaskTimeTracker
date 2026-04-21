package ru.testcdek.tasktimetracker.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI taskTimeTrackerOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("Task Time Tracker API")
                        .description("REST API for tasks and time records")
                        .version("1.0.0"));
    }

}
