package com.billfella.billfella_api.auth.dto;

public record RegisterRequest(
    String email,
    String password,
    String displayName
) {}