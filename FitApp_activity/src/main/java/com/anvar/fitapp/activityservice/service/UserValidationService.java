package com.anvar.fitapp.activityservice.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class UserValidationService {
    private final WebClient webClient;

    public UserValidationService(WebClient webClient) {
        this.webClient = webClient;
    }

    public boolean validateUser(String userId) {
        return Boolean.TRUE.equals(webClient.get()
                .uri("/api/{userId}}/validate", userId)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block());
    }
}
