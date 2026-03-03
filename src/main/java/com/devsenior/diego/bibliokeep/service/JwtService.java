package com.devsenior.diego.bibliokeep.service;

import java.util.List;
import java.util.Map;

import io.jsonwebtoken.Claims;

public interface JwtService {

    String generateToken(Map<String, Object> claims, String subject);

    String generateToken(Map<String, Object> claims, String subject, long expirationTimeMs);

    String generateToken(String subject);
    
    boolean validateToken(String token);

    Claims getClaims(String token);

    <T> T getClaim(String token, String claimKey, Class<T> clazz);

    <T> List<T> getClaimList(String token, String claimKey, Class<T> clazz);
    
    String getSubject(String token);

    long getExpirationTime();
}
