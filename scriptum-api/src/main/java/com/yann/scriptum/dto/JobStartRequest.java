package com.yann.scriptum.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record JobStartRequest(
        @NotNull(message = "Fichier requis") UUID mediaFileId,
        String targetLanguage
) {}