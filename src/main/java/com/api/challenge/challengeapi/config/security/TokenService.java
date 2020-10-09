package com.api.challenge.challengeapi.config.security;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.api.challenge.challengeapi.model.Responsible;
import com.api.challenge.challengeapi.repository.ResponsibleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {

    @Value("${challenge.api.jwt.expiration}")
    private String expiration;

    @Value("${challenge.api.jwt.secret}")
    private String secret;

    @Autowired
    private ResponsibleRepository responsibleRepository;

    public String generateToken(Authentication authentication) {
        Responsible logged = (Responsible) authentication.getPrincipal();
        Date today = new Date();
        Date expirationDate = new Date(today.getTime() + Long.parseLong(expiration));
        return Jwts.builder()
            .setIssuer("Challenge API")
            .setSubject(logged.getId().toString())
            .setIssuedAt(today)
            .setExpiration(expirationDate)
            .signWith(SignatureAlgorithm.HS256, secret)
            .compact();
    }

    public boolean isValidToken(String token) {
        try {
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
       
    }

    public Long getUserId(String token) {
       Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
       return Long.parseLong(claims.getSubject());
    }

    public String retrieveToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if(token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
            return null;
        }
        return token.substring(7, token.length());
    }

    public void clientAuthenticate(String token) {
        Long userId = getUserId(token);
        Responsible responsible = responsibleRepository.findById(userId).get();
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(responsible, null, responsible.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
