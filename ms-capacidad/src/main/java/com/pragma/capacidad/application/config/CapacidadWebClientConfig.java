package com.pragma.capacidad.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class CapacidadWebClientConfig {

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    public WebClient webClient(String baseUrl) {
        return webClientBuilder()
                .baseUrl(baseUrl)
                .build();
    }
}
