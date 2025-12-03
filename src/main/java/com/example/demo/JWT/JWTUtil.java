package com.example.demo.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;

import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JWTUtil {
	 
	
	    private String secret = "640d20912076cfcde9cb4a4c03cecefeb7a0b23cf001a2c4b6903e399b867958"; // viene del application.properties

	    private Key key;

	    @PostConstruct
	    public void init() {
	        this.key = Keys.hmacShaKeyFor(secret.getBytes());
	    }

	    // ====================== GENERACIÓN ======================

	    public String generateToken(String email, Map<String, Object> extraClaims) {
	        return Jwts.builder()
	                .setClaims(extraClaims)
	                .setSubject(email)
	                .setIssuedAt(new Date(System.currentTimeMillis()))
	                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
	                .signWith(key, SignatureAlgorithm.HS256)
	                .compact();
	    }


	    public String generateToken(String email) {
	        return generateToken(email, Map.of());
	    }

	    // ====================== VALIDACIÓN ======================

	    public Boolean validateToken(String token, String email) {
	        final String extractedUsername = extractUsername(token);
	        return (extractedUsername.equals(email) && !isTokenExpired(token));
	    }

	    public String extractUsername(String token) {
	        return extractClaim(token, Claims::getSubject);
	    }

	    public Date extractExpiration(String token) {
	        return extractClaim(token, Claims::getExpiration);
	    }

	    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
	        final Claims claims = extractAllClaims(token);
	        return claimsResolver.apply(claims);
	    }

	    private Claims extractAllClaims(String token) {
	        return Jwts.parserBuilder()
	                .setSigningKey(key)
	                .build()
	                .parseClaimsJws(token)
	                .getBody();
	    }

	    private Boolean isTokenExpired(String token) {
	        return extractExpiration(token).before(new Date());
	    }
}
