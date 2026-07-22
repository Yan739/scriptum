package com.yann.scriptum.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "Email requis") String email,
        @NotBlank(message = "Mot de passe requis") String password
) {}