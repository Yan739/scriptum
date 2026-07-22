package com.yann.scriptum.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI scriptumOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Scriptum API")
                        .description("Transcription audio/video vers texte avec Whisper")
                        .version("1.0.0"));
    }
}