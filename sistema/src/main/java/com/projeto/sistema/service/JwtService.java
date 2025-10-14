package com.projeto.sistema.service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Arrays;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.projeto.sistema.model.Cliente;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    // private final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();

        private final SecretKey SECRET_KEY;
    
    public JwtService() {
        // ✅ Converter string fixa para 32 bytes
        String secretString = "sua-chave-super-secreta-com-exatos-32-bytes!!";
        byte[] keyBytes = secretString.getBytes(StandardCharsets.UTF_8);
        
        // Garantir que tenha 32 bytes
        if (keyBytes.length < 32) {
            keyBytes = Arrays.copyOf(keyBytes, 32);
        } else if (keyBytes.length > 32) {
            keyBytes = Arrays.copyOf(keyBytes, 32);
        }
        
        this.SECRET_KEY = Keys.hmacShaKeyFor(keyBytes);

        System.out.println("Chave secreta (base64): " + SECRET_KEY.getEncoded());
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return (Date) extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String generateToken(UserDetails userDetails) {
        Cliente cliente = (Cliente) userDetails; // Certifique-se de que UserDetails é do tipo Cliente

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("id", cliente.getId())
                .claim("nome", cliente.getNome())
                .claim("email", cliente.getEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 24 horas
                .signWith(SECRET_KEY)
                .compact();
    }
    
}