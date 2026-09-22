package com.example.lab10;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfig {

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        // กำหนด baseUrl ของ Service หรือ Mock API ปลายทางที่ต้องการเรียก
        return builder
                .baseUrl("http://localhost:8080") 
                .build();
    }
}