package com.yayaveli.inventorymanagement.config;

import com.yayaveli.inventorymanagement.utils.Constants;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(
                        new ApiInfoBuilder()
                                .description("Inventory movement API documentation")
                                .title("Inventory Movement Rest API")
                                .build())
                .groupName("REST API V1")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.yayaveli.inventorymanagement"))
                // .paths(PathSelectors.any())
                .paths(PathSelectors.ant("/" + Constants.APP_ROOT + "/**"))
                .build();
    }
}
