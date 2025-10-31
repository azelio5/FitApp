package com.anvar.fitapp.userservice.DTO;


import com.anvar.fitapp.userservice.model.UserRole;
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

    private String email;

    private String password;

    private String firstName;
    private String lastName;

    private UserRole role;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
