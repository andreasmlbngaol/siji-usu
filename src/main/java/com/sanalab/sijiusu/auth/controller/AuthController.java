package com.sanalab.sijiusu.auth.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sanalab.sijiusu.auth.service.AuthService;
import com.sanalab.sijiusu.auth.service.JwtService;
import com.sanalab.sijiusu.core.util.Routing;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.sanalab.sijiusu.core.util.ResponseHelper.responseException;

@RestController
@RequestMapping(Routing.AUTH)
public class AuthController {
    private final AuthService authService;
    private final JwtService jwtService;

    @Autowired
    public AuthController(AuthService authService, JwtService jwtService) {
        this.authService = authService;
        this.jwtService = jwtService;
    }

    public record LoginRequest(
        @NotNull String identifier,
        @NotNull String password
    ) {}

    public record RefreshRequest(
        @JsonProperty("refresh_token")
        String refreshToken
    ) {}

    @PostMapping(Routing.LOGIN)
    public ResponseEntity<AuthService.TokenPair> login(
        @Valid @RequestBody LoginRequest body,
        @RequestParam(value = "web", defaultValue = "false") boolean isWeb,
        HttpServletResponse response
    ) {
        var tokenPair = authService.login(body.identifier, body.password);

        if (isWeb) return createCookieResponse(tokenPair, response);
        return ResponseEntity.ok(tokenPair);
    }

    private ResponseEntity<AuthService.TokenPair> createCookieResponse(
        AuthService.TokenPair tokenPair,
        HttpServletResponse response
    ) {
        ResponseCookie accessCookie = ResponseCookie.from("access_token", tokenPair.accessToken())
            .httpOnly(true)
            .path("/")
            .maxAge(jwtService.getAccessTokenValidityMs())
            .sameSite("Lax")
            .build();

        ResponseCookie refreshCookie = ResponseCookie.from("refresh_token", tokenPair.refreshToken())
            .httpOnly(true)
            .path("/")
            .maxAge(jwtService.getRefreshTokenValidityMs())
            .sameSite("Lax")
            .build();

        response.addHeader("Set-Cookie", accessCookie.toString());
        response.addHeader("Set-Cookie", refreshCookie.toString());

        return ResponseEntity.ok().build();
    }

    @PostMapping(Routing.REFRESH)
    public ResponseEntity<AuthService.TokenPair> refresh(
        @RequestParam(value = "web", defaultValue = "false") boolean isWeb,
        @RequestBody(required = false) RefreshRequest body,
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        String refreshToken;

        if(isWeb) {
            refreshToken = extractRefreshTokenFromCookies(request.getCookies());
            if( refreshToken == null) {
                throw responseException(HttpStatus.BAD_REQUEST, "Refresh token is missing in cookies");
            }
        } else {
            if (body == null || body.refreshToken() == null) {
                throw responseException(HttpStatus.BAD_REQUEST, "Refresh token is required");
            }
            refreshToken = body.refreshToken();
        }

        var tokenPair = authService.refresh(refreshToken);

        if (isWeb) return createCookieResponse(tokenPair, response);
        return ResponseEntity.ok(tokenPair);
    }

    @PostMapping(Routing.LOGOUT)
    public ResponseEntity<Void> logout(
        @RequestParam(value = "web", defaultValue = "false") boolean isWeb,
        @RequestBody(required = false) RefreshRequest body,
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        String refreshToken;

        if (isWeb) {
            refreshToken = extractRefreshTokenFromCookies(request.getCookies());
            if (refreshToken == null) {
                throw responseException(HttpStatus.BAD_REQUEST, "Refresh token is missing in cookies");
            }

            // Clear cookie
            ResponseCookie clearAccess = ResponseCookie.from("access_token", "")
                .httpOnly(true)
                .path("/")
                .maxAge(0)
                .sameSite("Lax")
                .build();

            ResponseCookie clearRefresh = ResponseCookie.from("refresh_token", "")
                .httpOnly(true)
                .path("/")
                .maxAge(0)
                .sameSite("Lax")
                .build();

            response.addHeader("Set-Cookie", clearAccess.toString());
            response.addHeader("Set-Cookie", clearRefresh.toString());
        } else {
            if (body == null || body.refreshToken() == null) {
                throw responseException(HttpStatus.BAD_REQUEST, "Refresh token is required");
            }
            refreshToken = body.refreshToken();
        }

        authService.logout(refreshToken);
        return ResponseEntity.ok().build();
    }

    public record ChangePasswordPayload(
        @NotNull String oldPassword,
        @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,}$",
            message = "Password must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, and one digit."
        )
        @NotNull String newPassword
    ) { }

    @PatchMapping(Routing.PASSWORD)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(
        @Valid @RequestBody ChangePasswordPayload payload
    ) {
        authService.changePassword(payload.oldPassword, payload.newPassword);
    }

    private String extractRefreshTokenFromCookies(Cookie[] cookies) {
        if (cookies == null) return null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("refresh_token")) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
