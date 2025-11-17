package com.anvar.fitapp.gateway.user.filter;

import com.anvar.fitapp.gateway.user.DTO.RegisterRequestDTO;
import com.anvar.fitapp.gateway.user.service.UserService;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class KeycloakUserSyncFilter implements WebFilter {
    private final UserService userService;

    public KeycloakUserSyncFilter(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String userId = exchange.getRequest().getHeaders().getFirst("X-User-ID");
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");
        RegisterRequestDTO registerRequestDTO = getUserDetails(token);

        if (userId == null) {
            userId = registerRequestDTO.getKeycloakId();
        }

        if (registerRequestDTO.getKeycloakId() != null && token != null) {
            String finalUserId = userId;
            return userService.validateUser(userId)
                    .flatMap(exist -> {
                        if (!exist) {
                            //Register user

                            if (registerRequestDTO != null) {
                                return userService.registerUser(registerRequestDTO).then(Mono.empty());
                            } else return Mono.empty();
                        } else log.info("User already exists, Skipping sync");
                        return Mono.empty();
                    }).then(Mono.defer(() -> {
                        ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                                .header("X-User-ID", finalUserId)
                                .build();
                        return chain.filter(exchange.mutate().request(mutatedRequest).build());
                    }));
        }

        return chain.filter(exchange);
    }

    private RegisterRequestDTO getUserDetails(String token) {
        try {
            String tokenWithoutBearer = token.replace("Bearer ", "").trim();
            SignedJWT signedJWT = SignedJWT.parse(tokenWithoutBearer);
            JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();

            return RegisterRequestDTO.builder()
                    .email(claimsSet.getStringClaim("email"))
                    .keycloakId(claimsSet.getStringClaim("sub"))
                    .password("dummy123")
                    .firstName(claimsSet.getStringClaim("given_name"))
                    .lastName(claimsSet.getStringClaim("family_name"))
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
