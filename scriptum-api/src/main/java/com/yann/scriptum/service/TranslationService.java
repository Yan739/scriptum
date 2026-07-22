package com.yann.scriptum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class TranslationService {

    @Value("${translation.api.url:https://api.mymemory.translated.net}")
    private String translationApiUrl;

    /** Traduit un texte vers la langue cible via l'API MyMemory. */
    public String translate(String text, String targetLanguage) {
        WebClient client = WebClient.builder().baseUrl(translationApiUrl).build();

        MyMemoryResponse response = client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/get")
                        .queryParam("q", text)
                        .queryParam("langpair", "auto|" + targetLanguage)
                        .build())
                .retrieve()
                .bodyToMono(MyMemoryResponse.class)
                .block();

        return response != null ? response.responseData().translatedText() : text;
    }

    private record MyMemoryResponse(ResponseData responseData) {}
    private record ResponseData(String translatedText) {}
}