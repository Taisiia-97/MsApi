package com.example.taisiia.ms.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record AuthenticationRequest(

        @Schema(description = "login", example = "user@gmail.com")
        @NotBlank(message = "Login is required")
        String login,
        @Schema(description = "password", example = "SomePassword1")
        @NotBlank(message = "Password is required")
        String password
) {
}
