package com.tonyycruz.credit.application.system.configuration

import org.springdoc.core.models.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    @Bean
    fun publicApi(): GroupedOpenApi? {
        return GroupedOpenApi
            .builder()
            .group("credit-application-system-public")
            .pathsToMatch("/api/customers/**", "/api/credits/**")
            .build()
    }
}