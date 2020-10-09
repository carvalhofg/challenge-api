package com.api.challenge.challengeapi.dto;

public class TokenDTO {

    
    private String token;
    private String tokenType;

    public TokenDTO(String token, String tokenType) {
        this.token = token;
        this.tokenType = tokenType;
    }

    public String getToken() {
        return token;
    }
    
    public String getTokenType() {
        return tokenType;
    }

    
}
