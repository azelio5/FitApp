package com.anvar.fitapp.activityservice.service;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Log4j2
public class UserValidationService {
    private final WebClient webClient;

    public UserValidationService(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://user-service").build();
    }

    public boolean validateUser(String userId) {
            log.warn("Validating user {}", userId);
        return Boolean.TRUE.equals(webClient.get()
                .uri("/api/users/{userId}/validate", userId)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block());
    }
}
