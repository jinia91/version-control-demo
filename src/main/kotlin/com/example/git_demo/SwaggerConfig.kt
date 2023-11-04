package com.example.git_demo

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {
    @Bean
    fun openAPI(): OpenAPI {
        val info: Info = Info()
            .title("Htim Payment API")
            .version("1.0")
            .description("결제 서비스")
        return OpenAPI()
            .components(Components())
            .info(info)
    }
}
