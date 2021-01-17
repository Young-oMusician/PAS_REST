package com.example.PAS_REST.restapp.auth;

import io.jsonwebtoken.*;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;

public class JWTGenerator {
    private static final Logger LOGGER = Logger.getLogger(JWTGenerator.class.getName());

    private static final String AUTHORITIES_KEY = "auth";
    private String secretKey;
    private long tokenValidity;

    @PostConstruct
    public void init() {
        this.secretKey = "secret";
        this.tokenValidity = TimeUnit.MINUTES.toMillis(10);   //10 minutes
    }

    public String createToken(String username, Set<String> authorities) {
        long now = (new Date()).getTime();

        return Jwts.builder()
                .setSubject(username)
                .claim(AUTHORITIES_KEY, String.join(",", authorities))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .setExpiration(new Date(now + tokenValidity))
                .compact();
    }

    public JWTCredential getCredential(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

        Set<String> authorities = new HashSet<>(Arrays.asList(claims.get(AUTHORITIES_KEY).toString().split(",")));

        return new JWTCredential(claims.getSubject(), authorities);
    }
    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken);
            LOGGER.log(Level.INFO, "");
            return true;
        } catch (SignatureException e) {
            LOGGER.log(Level.INFO, "Invalid JWT signature: {0}", e.getMessage());
            return false;
        }
    }
}
