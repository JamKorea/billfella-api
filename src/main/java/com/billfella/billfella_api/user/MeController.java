package com.billfella.billfella_api.user;

import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MeController {

    @GetMapping("/api/me")
    public Map<String, Object> me(Authentication auth) {
        // principal = userId (UUID string), details = claims map from filter
        String userId = (String) auth.getPrincipal();
        @SuppressWarnings("unchecked")
        Map<String, Object> claims = (Map<String, Object>) auth.getDetails();

        return Map.of(
                "userId", userId,
                "email", claims.get("email"),
                "name", claims.get("name")
        );
    }
}