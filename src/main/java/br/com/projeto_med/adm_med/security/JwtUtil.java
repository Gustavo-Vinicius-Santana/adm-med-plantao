package br.com.projeto_med.adm_med.security;

import br.com.projeto_med.adm_med.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {
    private final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(
            "minha_chave_super_secreta_deve_ter_no_minimo_32_chars".getBytes()
    );
    
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
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Extrai o ID do usuário do token
    public Long extractUserId(String token) {
        return extractClaim(token, claims -> Long.parseLong(claims.get("userId").toString()));
    }

    // Gera token incluindo o ID do usuário
    public String generateToken(UserDetails userDetails, Long userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", userDetails.getAuthorities()); // opcional: roles
        claims.put("userId", userId); // inclui ID do usuário
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        long expirationTime = 1000 * 60 * 60; // 1h
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject) // e-mail
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public Boolean validateToken(String token, Usuario userDetails) {
        Long tokenUserId = extractUserId(token);
        return tokenUserId.equals(userDetails.getId()) && !isTokenExpired(token);
    }
}