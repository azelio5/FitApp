package com.anvar.fitapp.gateway.user.DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserResponseDTO {

    private String id;

    private String keycloakId;

    private String email;

    private String password;

    private String firstName;
    private String lastName;

//    private UserRole role;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
