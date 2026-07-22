package com.yann.scriptum.dto;

import java.time.Instant;
import java.util.UUID;

public record MediaFileResponse(
        UUID id,
        String originalFilename,
        String mimeType,
        long sizeBytes,
        Integer durationSeconds,
        Instant uploadedAt
) {}