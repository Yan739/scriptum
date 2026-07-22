package com.yann.scriptum.dto;

public record AuthResponse(
        String token,
        String email
) {}