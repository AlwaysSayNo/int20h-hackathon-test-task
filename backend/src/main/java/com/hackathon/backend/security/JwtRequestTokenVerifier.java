package com.hackathon.backend.security;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * This filter checks if the token is valid
 */
@Component
public class JwtRequestTokenVerifier extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;

    @Autowired
    public JwtRequestTokenVerifier(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // execute a "replace" method to remove a "Bearer " word
            final String jwtToken = authorizationHeader.replace("Bearer ", "");

            try {
                String userLogin = jwtProvider.extractUserLogin(jwtToken);
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        userLogin, null, null);

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (JwtException e) {
                throw new IllegalStateException(String.format("Token %s cannot be trusted", jwtToken));
            }
        }
        filterChain.doFilter(request, response);
    }
}
