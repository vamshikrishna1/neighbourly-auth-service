package com.neighbourly.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @Email(message = "Invalid email") @NotBlank String email,
        @NotBlank String password
) {}
