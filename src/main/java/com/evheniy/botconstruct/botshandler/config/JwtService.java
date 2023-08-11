package com.evheniy.botconstruct.botshandler.config;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.*;
import java.util.function.Function;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;




@Service
@Slf4j
public class JwtService {
    private static final String PRIVATE_KEY = "3615f80c9d293ed7402687f94b22d58e529b8cc7916f8fac7fddf7fbd5af4cf777d3d795a7a00a16bf7e7f3fb9561ee9baae480da9fe7a18769e71886b03f315";


    public String extractPhone(String JwtToken) {
        log.info(extractClaim(JwtToken, Claims::getSubject));
        return extractClaim(JwtToken, Claims::getSubject);
    }

    public <T> T extractClaim(String jwtToken, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(jwtToken);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String JwtToken) {
        try {
            return Jwts
                    .parserBuilder()
                    .setSigningKey(PRIVATE_KEY)
                    .build()
                    .parseClaimsJws(JwtToken)
                    .getBody();
        } catch (JwtException e){
            log.error("Failed to parse JWT token", e);
            throw new JwtAuthenticationException("JWT token is expired or invalid");
        }
    }

    public String generateJwtToken(UserDetails userDetails) {
        return generateJwtToken(new HashMap<>(), userDetails);
    }

    public String generateJwtToken(
            Map<String, Object> extractClaims,
            UserDetails userDetails
    ) {
        extractClaims.put("roles", "ROLE_USER");
        return Jwts
                .builder()
                .setClaims(extractClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(SignatureAlgorithm.HS512, PRIVATE_KEY)
                .compact();
    }

    public boolean isTokenValid(String jwtToken, UserDetails userDetails) {
        final Claims claims = extractAllClaims(jwtToken);
        if (claims == null) {
            return false;
        }

        final String phone = extractPhone(jwtToken);
        return (phone.equals(userDetails.getUsername())) && !isJwtTokenExpired(claims);
    }

    private boolean isJwtTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }
}
