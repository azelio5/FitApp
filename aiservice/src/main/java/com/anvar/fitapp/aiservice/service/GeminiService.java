package com.anvar.fitapp.aiservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class GeminiService {

    private final WebClient webClient;
    private final String geminiUri;
    private final String geminiKey;

    public GeminiService(WebClient.Builder webClientBuilder,
                         @Value("${gemini.api.url}") String geminiUri,
                         @Value("${gemini.api.key}") String geminiKey) {
        this.webClient = webClientBuilder.build();
        this.geminiUri = geminiUri;
        this.geminiKey = geminiKey;
    }


    public String getAnswer(String question) {
        Map<String, Object> requestBody = Map.of(
                "contents", new Object[]{
                        Map.of("parts", new Object[]{
                                Map.of("text", question),
                        })
                });

        return  webClient.post()
                .uri(geminiUri + geminiKey)
                .header("Content-Type", "application/json")
                .bodyValue(requestBody
                ).retrieve().bodyToMono(String.class).block();
    }


}
