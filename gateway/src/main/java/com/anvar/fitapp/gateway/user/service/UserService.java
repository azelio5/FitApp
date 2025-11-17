package com.anvar.fitapp.gateway.user.service;

import com.anvar.fitapp.gateway.user.DTO.RegisterRequestDTO;
import com.anvar.fitapp.gateway.user.DTO.UserResponseDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Service
@Log4j2
public class UserService {
    private final WebClient webClient;

    public UserService(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://user-service").build();
    }

    public Mono<Boolean> validateUser(String userId) {
        log.info("Calling User Service for {}", userId);
        return webClient.get()
                .uri("/api/users/{userId}/validate", userId)
                .retrieve()
                .bodyToMono(Boolean.class)
                .onErrorResume(WebClientResponseException.class, e -> {
                    if (e.getStatusCode() == HttpStatus.NOT_FOUND)
                        return Mono.error(new RuntimeException("User not found : " + userId));

                    else if (e.getStatusCode() == HttpStatus.BAD_REQUEST)
                        return Mono.error(new RuntimeException("Invalid : " + userId));

                    return Mono.error(new RuntimeException("Unexpected error : " + userId));
                });
    }

    public Mono<UserResponseDTO> registerUser(RegisterRequestDTO request) {
        log.info("Calling User Registration API for email:{}", request.getEmail());
        return webClient.post()
                .uri("/api/users/create")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(UserResponseDTO.class)
                .onErrorResume(WebClientResponseException.class, e -> {
                    if (e.getStatusCode() == HttpStatus.BAD_REQUEST)
                        return Mono.error(new RuntimeException("Bad request : " + e.getMessage()));

                    else if (e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR)
                        return Mono.error(new RuntimeException("Internal server error : " + e.getMessage()));

                    return Mono.error(new RuntimeException("Unexpected error : " + e.getMessage()));
                });
    }
}
