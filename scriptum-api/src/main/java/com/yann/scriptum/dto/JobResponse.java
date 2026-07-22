package com.yann.scriptum.dto;

import com.yann.scriptum.enums.JobStatus;

import java.time.Instant;
import java.util.UUID;

public record JobResponse(
        UUID id,
        UUID mediaFileId,
        JobStatus status,
        int progress,
        String sourceLanguage,
        String targetLanguage,
        String rawText,
        String translatedText,
        String errorMessage,
        Instant createdAt,
        Instant updatedAt
) {}