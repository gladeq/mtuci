package configuration;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    //TODO: реализовать методы создания, валидации и получения информации из JWT токена

    public String createToken(String username, Set<GrantedAuthority> authorities) {
        Claims claims = Jwts.claims().setSubject(username);
        // Correctly collect authorities
        claims.put("authorities", authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));

        Date now = new Date();
        Date validity = new Date(now.getTime() + expiration * 1000);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            // More specific exception handling for better diagnostics
            System.err.println("Invalid JWT token: " + e.getMessage());
            return false;
        }
    }

    public String getUsername(String token) {
        Claims claims = parseClaims(token);
        return claims.getSubject();
    }

    public Set<GrantedAuthority> getAuthorities(String token) {
        Claims claims = parseClaims(token);
        return ((List<String>) claims.get("authorities")).stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }


    // Helper method to parse the token and handle potential exceptions.
    private Claims parseClaims(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
        } catch (SecurityException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            throw new RuntimeException("JWT token is invalid", e); // Re-throw as RuntimeException
        }
    }
}