package ru.mtuci.rbpomtuci2024.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {


    private final UserDetailsService userDetailsService;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    private long validityInMilliseconds = expiration * 1000;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String createToken(String username, String role) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("role", role);

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(getSigningKey())
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {

            log.error("Invalid JWT token: {}", e.getMessage());
            return false;
        }
    }

    public String getUsername(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (JwtException | IllegalArgumentException e) {
            log.error("Error getting username from JWT token: {}", e.getMessage());
            return null;
        }
    }

    public Set<GrantedAuthority> getAuthorities(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            List<?> authorities = claims.get("role", List.class);
            if (authorities == null || authorities.isEmpty()) {
                return Collections.emptySet();
            }

            // Проверка типа элементов списка перед приведением к String
            if (authorities.stream().anyMatch(a -> !(a instanceof String))) {
                log.error("Invalid authority type in JWT token");
                return Collections.emptySet();
            }

            return authorities.stream()
                    .map(authority -> new SimpleGrantedAuthority((String) authority))
                    .collect(Collectors.toSet());
        } catch (JwtException | IllegalArgumentException | ClassCastException e) {
            log.error("Error getting authorities from JWT token: {}", e.getMessage());
            return Collections.emptySet();
        }
    }

    public Authentication getAuthentication(String token) {
        String username = getUsername(token);
        if (username != null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            Set<GrantedAuthority> authorities = getAuthorities(token);
            return new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
        } else {
            return null;
        }
    }


    public Claims getTokenClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.error("Error parsing JWT token: {}", e.getMessage());
            return null;
        }
    }
}
