package com.sanalab.sijiusu.auth.component;

import com.sanalab.sijiusu.auth.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.lang.NonNull;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtService jwtService;

    public JwtAuthFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public void doFilterInternal(
        HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        String token = request.getHeader("Authorization");

        if(token == null || !token.startsWith("Bearer ")) {
            if(request.getCookies() != null) {
                token = extractTokenFromCookie(request);
            }
        }

        if(token != null && jwtService.validateAccessToken(token)) {
            long userId = jwtService.getUserIdFromToken(token);
            var auth = new UsernamePasswordAuthenticationToken(
                userId,
                null,
                Collections.emptyList()
            );
            SecurityContextHolder.getContext().setAuthentication(auth);

        }

        filterChain.doFilter(request, response);
//        if(token != null && token.startsWith("Bearer ")) {
//            if(jwtService.validateAccessToken(token)) {
//                long userId = jwtService.getUserIdFromToken(token);
//                var auth = new UsernamePasswordAuthenticationToken(
//                    userId,
//                    null,
//                    Collections.emptyList()
//                );
//                SecurityContextHolder.getContext().setAuthentication(auth);
//            }
//        }
//
//        if(request.getCookies() != null) {
//                token = extractTokenFromCookie(request);
//            if(token != null && jwtService.validateAccessToken(token)) {
//                long userId = jwtService.getUserIdFromToken(token);
//                var auth = new UsernamePasswordAuthenticationToken(
//                    userId,
//                    null,
//                    Collections.emptyList()
//                );
//                SecurityContextHolder.getContext().setAuthentication(auth);
//            }
//        }


    }

    private String extractTokenFromCookie(HttpServletRequest request) {
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals("access_token")) {
                return cookie.getValue();
            }
        }
        return null;
    }

}
