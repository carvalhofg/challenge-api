package com.api.challenge.challengeapi.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;


public class TokenAuthenticationFilter extends OncePerRequestFilter {
    
    private TokenService tokenService;

    public TokenAuthenticationFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = tokenService.retrieveToken(request);
        boolean valido = tokenService.isValidToken(token);
        if(valido) {
            tokenService.clientAuthenticate(token);
        }

        filterChain.doFilter(request, response);
    }
}
