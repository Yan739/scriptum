package com.yann.scriptum.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank(message = "Email requis") @Email(message = "Email invalide") String email,
        @NotBlank(message = "Mot de passe requis") @Size(min = 8, message = "Le mot de passe doit contenir au moins 8 caracteres") String password
) {}