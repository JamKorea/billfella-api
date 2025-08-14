package com.billfella.billfella_api.auth.dto;

public record LoginRequest(
    String email,
    String password
) {}