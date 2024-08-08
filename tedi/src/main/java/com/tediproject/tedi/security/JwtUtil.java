package com.tediproject.tedi.security;

import java.security.Key;
import java.util.Date;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    private final long EXPIRATION_TIME = 1800_000; // half an hour

    public String getEmailFromJWT(String token){
		Claims claims = Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody();
		return claims.getSubject();
	}

    // public Date extractExpiration(String token) {
    //     return extractClaim(token, Claims::getExpiration);
    // }

    // public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    //     final Claims claims = extractAllClaims(token);
    //     return claimsResolver.apply(claims);
    // }

    // private Claims extractAllClaims(String token) {
    //     return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
    // }

    public String generateToken(Authentication auth) {
        String username = auth.getName();
		Date currentDate = new Date();
		Date expireDate = new Date(currentDate.getTime() + EXPIRATION_TIME);
		
		String token = Jwts.builder()
				.setSubject(username)
				.setIssuedAt( new Date())
				.setExpiration(expireDate)
				.signWith(key,SignatureAlgorithm.HS512)
				.compact();
		System.out.println("New token :");
		System.out.println(token);
		return token;
    }

    // private String createToken(Map<String, Object> claims, String subject) {
    //     return Jwts.builder()
    //             .setClaims(claims)
    //             .setSubject(subject)
    //             .setIssuedAt(new Date(System.currentTimeMillis()))
    //             .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
    //             .signWith(SignatureAlgorithm.HS256, SECRET)
    //             .compact();
    // }

    // public boolean isTokenExpired(String token) {
    //     return extractExpiration(token).before(new Date());
    // }

    public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(token);
			return true;
		} catch (Exception ex) {
			throw new AuthenticationCredentialsNotFoundException("JWT was exprired or incorrect",ex.fillInStackTrace());
		}
	}

}

