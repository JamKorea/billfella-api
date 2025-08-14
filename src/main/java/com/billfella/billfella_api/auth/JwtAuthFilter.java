package com.billfella.billfella_api.auth;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwt;

    public JwtAuthFilter(JwtService jwt) {
        this.jwt = jwt;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {
        String auth = req.getHeader("Authorization");
        if (auth != null && auth.startsWith("Bearer ")) {
            String token = auth.substring(7);
            try {
                Jws<Claims> jws = jwt.parse(token);
                Claims c = jws.getPayload();

                String userId = c.getSubject(); // we set subject to user UUID
                // put useful claims into details so controllers can read them
                Map<String, Object> details = Map.of(
                        "email", c.get("email"),
                        "name", c.get("name")
                );

                var authToken = new UsernamePasswordAuthenticationToken(
                        userId, null, Collections.emptyList()
                );
                authToken.setDetails(details);
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } catch (Exception ignored) {
                // invalid/expired token → leave context unauthenticated
            }
        }
        chain.doFilter(req, res);
    }
}