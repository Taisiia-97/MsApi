package com.example.taisiia.ms.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterUserRequest(

        @Schema(description = "login", example = "user@gmail.com")
        @NotBlank(message = "Login is required")
        String login,
        @Schema(description = "password", example = "SomePassword1")
        @NotBlank(message = "Password is required")
        @Size(min = 8, max = 20)
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
                message = "Password must have at least one uppercase letter, one lowercase letter, one number, and one special character"
        )
        String password
) {
}
