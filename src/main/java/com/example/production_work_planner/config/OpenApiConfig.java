package com.example.production_work_planner.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI productionWorkPlannerOpenApi(){
        return new OpenAPI()
                .info(new Info()
                        .title("Production Work Planner API")
                        .description("REST API for planning and tracking production work tasks")
                        .version("1.0.0"));


    }
}
