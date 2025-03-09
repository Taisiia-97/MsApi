package com.example.taisiia.ms.domain.dto;

import jakarta.validation.constraints.NotBlank;

public record RegisterItemRequest(
        @NotBlank
        String name) {
}
