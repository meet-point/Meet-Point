package ru.meetpoint.authservice.jwt;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.meetpoint.authservice.config.property.AuthConfigProperties;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Instant;
import java.util.*;

@Slf4j
@Component
public class JwtProvider {

    private final JwtParser jwtParser;

    private final PublicKey publicKey;

    private final PrivateKey privateKey;

    private final AuthConfigProperties authConfigProperties;

    public JwtProvider(AuthConfigProperties authConfigProperties) {
        this.authConfigProperties = authConfigProperties;
        this.privateKey = loadPrivateKey(authConfigProperties.privateKey());
        this.publicKey = loadPublicKey(authConfigProperties.publicKey());
        this.jwtParser = Jwts.parser().verifyWith(publicKey).build();
    }

    public String generateAccessToken(String subject, Map<String, Object> claims) {
        Instant now = Instant.now();
        Instant expiry = now.plusSeconds(authConfigProperties.accessTokenExpiration().toSeconds());

        Map<String, Object> processedClaims = processClaimsForGeneration(claims);
        return Jwts.builder()
                .subject(subject)
                .claims(processedClaims)
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiry))
                .signWith(privateKey, Jwts.SIG.RS256)
                .compact();
    }

    public String generateRefreshToken(String subject) {
        Instant now = Instant.now();
        Instant expiry = now.plusSeconds(authConfigProperties.refreshTokenExpiration().toSeconds());

        return Jwts.builder()
                .subject(subject)
                .claim("jti", UUID.randomUUID().toString())
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiry))
                .signWith(privateKey, Jwts.SIG.RS256)
                .compact();
    }

    public boolean isTokenValid(String token) {
        try {
            jwtParser.parseSignedClaims(token);
            return true;
        } catch (JwtException jwtException) {
            log.warn("JwtException: {}", jwtException.getMessage());
            return false;
        }
    }

    public String extractSubject(String token) {
        return jwtParser.parseSignedClaims(token).getPayload().getSubject();
    }

    public Claims extractAllClaims(String token) {
        return jwtParser.parseSignedClaims(token).getPayload();
    }

    public UUID extractUuidClaim(String token, String claimName) {
        return UUID.fromString(extractAllClaims(token).get(claimName, String.class));
    }

    private PublicKey loadPublicKey(String publicKey) {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(publicKey);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            return KeyFactory.getInstance("RSA").generatePublic(keySpec);
        } catch (Exception exception) {
            throw new IllegalArgumentException("Failed to load public key", exception);
        }
    }

    private PrivateKey loadPrivateKey(String privateKey) {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(privateKey);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            return KeyFactory.getInstance("RSA").generatePrivate(keySpec);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to load private key", e);
        }
    }

    private Map<String, Object> processClaimsForGeneration(Map<String, Object> claims) {
        Map<String, Object> processedClaims = new HashMap<>(claims);

        processedClaims.replaceAll((key, value) -> {
            if (value instanceof UUID) {
                return value.toString();
            } else if (value instanceof Collection) {
                return new ArrayList<>((Collection<?>) value);
            }
            return value;
        });

        return processedClaims;
    }
}
