package com.addrsearch.app.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {
    @Bean
    fun openAPI(): OpenAPI {
        val info = Info()
            .title("전국 검색모듈")
            .version("1.0")
            .description("SDSP API 문서")
        return OpenAPI()
            .components(Components())
            .info(info)
    }
}