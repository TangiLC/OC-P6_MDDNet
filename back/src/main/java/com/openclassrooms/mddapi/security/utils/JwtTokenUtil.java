package com.openclassrooms.mddapi.security.utils;

import com.openclassrooms.mddapi.security.UserPrincipal;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenUtil {

  private static final Logger logger = LoggerFactory.getLogger(
    JwtTokenUtil.class
  );

  @Value("${jwt.secretKey}")
  private String secretKey;

  @Value("${jwt.validity}")
  private long validityMs;

  private SecretKey getSigningKey() {
    byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  public String generateToken(UserPrincipal userPrincipal) {
    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + validityMs);

    return Jwts
      .builder()
      .subject(userPrincipal.getUsername())
      .issuedAt(now)
      .expiration(expiryDate)
      .signWith(getSigningKey())
      .compact();
  }

  public String getUsernameFromToken(String token) {
    return Jwts
      .parser()
      .verifyWith(getSigningKey())
      .build()
      .parseSignedClaims(token)
      .getPayload()
      .getSubject();
  }

  public boolean validateToken(String token) {
    try {
      Jwts
        .parser()
        .verifyWith(getSigningKey())
        .build()
        .parseSignedClaims(token);

      // Si on arrive ici, cela signifie que le token est valide (signature correcte)
      // et n'est pas expiré (sinon une ExpiredJwtException aurait été levée)
      return true;
    } catch (SignatureException e) {
      logger.error("Signature JWT invalide: {}", e.getMessage());
    } catch (MalformedJwtException e) {
      logger.error("Token JWT mal formé: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      logger.error("Token JWT expiré: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      logger.error("Token JWT non supporté: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      logger.error(
        "La chaîne de revendications JWT est vide: {}",
        e.getMessage()
      );
    }

    return false;
  }

  private boolean isTokenExpired(String token) {
    Date expiration = Jwts
      .parser()
      .verifyWith(getSigningKey())
      .build()
      .parseSignedClaims(token)
      .getPayload()
      .getExpiration();
    return expiration.before(new Date());
  }
}
