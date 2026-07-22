package com.yann.scriptum.dto;

import com.yann.scriptum.enums.JobStatus;

import java.util.UUID;

public record ProgressMessage(
        UUID jobId,
        JobStatus status,
        int progress
) {}