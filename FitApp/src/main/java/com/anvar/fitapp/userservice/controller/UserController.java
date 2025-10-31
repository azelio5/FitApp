package com.anvar.fitapp.userservice.controller;

import com.anvar.fitapp.userservice.DTO.RegisterRequestDTO;
import com.anvar.fitapp.userservice.DTO.UserResponseDTO;
import com.anvar.fitapp.userservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable("userId") String userId) {
        return ResponseEntity.ok(userService.getUser(userId));
    }

    @GetMapping("/{userId}/validate")
    public ResponseEntity<Boolean> validateUser(@PathVariable("userId") String userId) {
        return ResponseEntity.ok(userService.existByUseId(userId));
    }

    @PostMapping("/create")
    public ResponseEntity<UserResponseDTO> create(@Valid @RequestBody RegisterRequestDTO registerRequestDTO) {
        return ResponseEntity.ok(userService.create(registerRequestDTO));
    }
}
