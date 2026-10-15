package org.sopt.core.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.sopt.core.support.swagger.ApiExceptionsOperationCustomizer;
import org.sopt.core.support.swagger.ApiSuccessOperationCustomizer;
import org.springdoc.core.customizers.GlobalOperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("SOPT 39기 서버 과제 API")
                        .description("API 문서")
                        .version("v1"));
    }

    @Bean
    public GlobalOperationCustomizer apiExceptionsOperationCustomizer() {
        return new ApiExceptionsOperationCustomizer();
    }

    @Bean
    public GlobalOperationCustomizer apiSuccessOperationCustomizer() {
        return new ApiSuccessOperationCustomizer();
    }
}
