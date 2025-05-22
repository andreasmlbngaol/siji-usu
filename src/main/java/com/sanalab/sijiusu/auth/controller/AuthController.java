package com.sanalab.sijiusu.auth.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sanalab.sijiusu.auth.service.AuthService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    public record LoginRequest(
        @NotNull String identifier,
        @NotNull String password
    ) {}

    public record RefreshRequest(
        @JsonProperty("refresh_token")
        @NotNull String refreshToken
    ) {}

    @PostMapping("/login")
    public AuthService.TokenPair login(
        @Valid @RequestBody LoginRequest body
    ) {
        return authService.login(
            body.identifier,
            body.password
        );
    }

    @PostMapping("/refresh")
    public AuthService.TokenPair refresh(
        @Valid @RequestBody RefreshRequest body
    ) {
        return authService.refresh(body.refreshToken);
    }

    @PostMapping("/logout")
    public void logout(
        @Valid @RequestBody RefreshRequest body
    ) {
        authService.logout(body.refreshToken);
    }
}
