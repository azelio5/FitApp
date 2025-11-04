package com.anvar.fitapp.userservice.service;

import com.anvar.fitapp.userservice.DTO.RegisterRequestDTO;
import com.anvar.fitapp.userservice.DTO.UserResponseDTO;
import com.anvar.fitapp.userservice.exception.NotFoundException;
import com.anvar.fitapp.userservice.model.User;
import com.anvar.fitapp.userservice.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public UserResponseDTO getUser(String userId) {
        User user = repository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));

        log.info("User found: {}", user);

        return UserResponseDTO.builder()
                .id(user.getId())
                .password(user.getPassword())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }


    public UserResponseDTO create(RegisterRequestDTO registerRequestDTO) {

        if (repository.existsByEmail(registerRequestDTO.getEmail())) {
            log.info("Email already exists");
            throw new RuntimeException("Email already exists");
        }

        User user = User.builder()
                .email(registerRequestDTO.getEmail())
                .password(registerRequestDTO.getPassword())
                .firstName(registerRequestDTO.getFirstName())
                .lastName(registerRequestDTO.getLastName())
                .build();

        repository.save(user);

        log.info("User created: {}", user);

        return UserResponseDTO.builder()
                .id(user.getId())
                .password(user.getPassword())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    public Boolean existByUseId(String userId) {
        log.info("Calling user validation API for userId: {}", userId);
        return repository.existsById(userId);
    }
}
