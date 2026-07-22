package com.yann.scriptum.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${groq.api.key}")
    private String groqApiKey;

    @Value("${whisper.local.url:http://localhost:5000}")
    private String whisperLocalUrl;

    @Bean
    public WebClient groqWebClient() {
        return WebClient.builder()
                .baseUrl("https://api.groq.com/openai/v1")
                .defaultHeader("Authorization", "Bearer " + groqApiKey)
                .build();
    }

    @Bean
    public WebClient whisperLocalWebClient() {
        return WebClient.builder()
                .baseUrl(whisperLocalUrl)
                .build();
    }
}