package com.yann.scriptum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TranslationService {

    private static final int MAX_BYTES_PER_REQUEST = 450; // marge de securite sous la limite de 500

    @Value("${translation.api.url:https://api.mymemory.translated.net}")
    private String translationApiUrl;

    /** Traduit un texte vers la langue cible via l'API MyMemory, en decoupant si le texte depasse la limite de l'API. */
    public String translate(String text, String sourceLanguage, String targetLanguage) {
        WebClient client = WebClient.builder().baseUrl(translationApiUrl).build();
        List<String> chunks = splitByByteSize(text, MAX_BYTES_PER_REQUEST);

        StringBuilder result = new StringBuilder();
        for (String chunk : chunks) {
            String translated = translateChunk(client, chunk, sourceLanguage, targetLanguage);
            result.append(translated).append(" ");
        }

        return result.toString().trim();
    }

    private String translateChunk(WebClient client, String text, String sourceLanguage, String targetLanguage) {
        MyMemoryResponse response = client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/get")
                        .queryParam("q", text)
                        .queryParam("langpair", sourceLanguage + "|" + targetLanguage)
                        .build())
                .retrieve()
                .bodyToMono(MyMemoryResponse.class)
                .block();

        return response != null ? response.responseData().translatedText() : text;
    }

    private List<String> splitByByteSize(String text, int maxBytes) {
        List<String> chunks = new ArrayList<>();
        String[] sentences = text.split("(?<=[.!?])\\s+");

        StringBuilder current = new StringBuilder();
        for (String sentence : sentences) {
            if ((current.toString() + sentence).getBytes(StandardCharsets.UTF_8).length > maxBytes) {
                if (!current.isEmpty()) {
                    chunks.add(current.toString().trim());
                    current = new StringBuilder();
                }
            }
            current.append(sentence).append(" ");
        }
        if (!current.isEmpty()) {
            chunks.add(current.toString().trim());
        }

        return chunks;
    }

    private record MyMemoryResponse(ResponseData responseData) {}
    private record ResponseData(String translatedText) {}
}