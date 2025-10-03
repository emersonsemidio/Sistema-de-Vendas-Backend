// package com.projeto.sistema.service;

// import java.time.Instant;
// import java.time.LocalDateTime;
// import java.time.ZoneOffset;

// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.stereotype.Service;

// import com.auth0.jwt.JWT;
// import com.auth0.jwt.algorithms.Algorithm;
// import com.auth0.jwt.exceptions.JWTCreationException;
// import com.auth0.jwt.exceptions.JWTVerificationException;
// import com.projeto.sistema.model.Cliente;

// @Service
// public class TokenService {
//   @Value("${api.security.token.secret}")
//   private String secret;
//   public String gerarToken(Cliente cliente) {
//     try {
//       Algorithm algorithm = Algorithm.HMAC256(secret);
//       String token = JWT.create()
//           .withIssuer("sistema-de-vendas")
//           .withSubject(cliente.getEmail())
//           .withExpiresAt(getExpiresAt())
//           .sign(algorithm);
//        return token;
//     } catch (JWTCreationException e) {
//       throw new RuntimeException("Erro ao gerar token JWT", e);
//     }
//   }

//   public String validarToken(String token) {
//     try {
//       Algorithm algorithm = Algorithm.HMAC256(secret);
//       return JWT.require(algorithm)
//           .withIssuer("sistema-de-vendas")
//           .build()
//           .verify(token)
//           .getSubject();
//     } catch (JWTVerificationException e) {
//       return "";
//     }
//   }

//     private Instant getExpiresAt() {
//       return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
//   }
// }
