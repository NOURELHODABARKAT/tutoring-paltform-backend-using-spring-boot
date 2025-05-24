// package com.nour.demo.security;

// import java.security.Key;
// import java.util.Date;

// import javax.crypto.spec.SecretKeySpec;

// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.stereotype.Component;

// import io.jsonwebtoken.ExpiredJwtException;
// import io.jsonwebtoken.Jwts;
// import io.jsonwebtoken.MalformedJwtException;
// import io.jsonwebtoken.SignatureAlgorithm;
// import io.jsonwebtoken.UnsupportedJwtException;
// import io.jsonwebtoken.security.SignatureException;

// /**
//  * A utility class for JWT token generation and management.
//  * This class handles the creation of JWT tokens for authenticated users.
//  */
// @Component
// public class JwtToken {

//     @Value("${jwt.expiration}")
//     private int jwtExpiration;

//     @Value("${jwt.secret}")
//     private String jwtSecret;

//     public String generateToken(Authentication authentication) {
//         UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//         Date now = new Date();
//         Key key = new SecretKeySpec(jwtSecret.getBytes(), SignatureAlgorithm.HS512.getJcaName());
//         return Jwts.builder()
//                 .setSubject(userDetails.getUsername())
//                 .setIssuedAt(now)
//                 .setExpiration(new Date(now.getTime() + jwtExpiration * 1000))
//                 .signWith(key, SignatureAlgorithm.HS512)
//                 .compact();
//     }

//     public boolean validateToken(String token) {
//         try {
//             Jwts.parserBuilder().setSigningKey(jwtSecret.getBytes()).build().parseClaimsJws(token);
//             return true;
//         } catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
//             // Invalid or unsupported token, token expired, or token is empty/null
//         }
//         return false;
//     }

//     public String getUsernameFromToken(String token) {
//         return Jwts.parserBuilder().setSigningKey(jwtSecret.getBytes()).build().parseClaimsJws(token).getBody().getSubject();
//     }
// }