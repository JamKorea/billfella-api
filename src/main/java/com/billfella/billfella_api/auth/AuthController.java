package com.billfella.billfella_api.auth;

import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.billfella.billfella_api.auth.dto.LoginRequest;
import com.billfella.billfella_api.auth.dto.RegisterRequest;
import com.billfella.billfella_api.auth.dto.TokenResponse;
import com.billfella.billfella_api.user.User;
import com.billfella.billfella_api.user.UserRepository;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository users;
    private final PasswordEncoder encoder;
    private final JwtService jwt;

    public AuthController(UserRepository users, PasswordEncoder encoder, JwtService jwt) {
        this.users = users;
        this.encoder = encoder;
        this.jwt = jwt;
    }

    @PostMapping("/register")
    public TokenResponse register(@RequestBody RegisterRequest req) {
        String email = req.email().toLowerCase();
        if (users.existsByEmail(email)) {
            throw new RuntimeException("Email already registered");
        }
        User u = User.of(email, encoder.encode(req.password()), req.displayName());
        users.save(u);

        String token = jwt.create(
            u.getId().toString(),
            Map.of("email", u.getEmail(), "name", u.getDisplayName())
        );
        return new TokenResponse(token);
    }

    @PostMapping("/login")
    public TokenResponse login(@RequestBody LoginRequest req) {
        String email = req.email().toLowerCase();
        User u = users.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!encoder.matches(req.password(), u.getPasswordHash())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwt.create(
            u.getId().toString(),
            Map.of("email", u.getEmail(), "name", u.getDisplayName())
        );
        return new TokenResponse(token);
    }
}