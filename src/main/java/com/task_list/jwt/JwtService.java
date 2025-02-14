package com.task_list.jwt;

import com.task_list.jwt.token.entity.Token;
import com.task_list.jwt.token.repository.TokenRepositoryMongo;
import com.task_list.user.entity.MyUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;

@Configuration
@RequiredArgsConstructor
public class JwtService {

    private final TokenRepositoryMongo tokenRepositoryMongo;
    @Value("${secret.key}")
    private String secret;
    private static final Long TIME_DURATION_ACCESS = TimeUnit.MINUTES.toMillis(6);
    private static final Long TIME_DURATION_REFRESH = TimeUnit.DAYS.toMillis(16);

    public String generateTokenAccess(MyUser user) {
        return generateTokenByExpiration(user, TIME_DURATION_ACCESS);
    }

    public String generateTokenRefresh(MyUser user) {
        return generateTokenByExpiration(user, TIME_DURATION_REFRESH);
    }

    private String generateTokenByExpiration(MyUser myUser, Long expiration){
        return Jwts.builder()
                .subject(myUser.getUsername())
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusMillis(expiration)))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    private KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
        keyPairGenerator.initialize(256);
        return keyPairGenerator.generateKeyPair();
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractUserEmail(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    public boolean isValidToken(String token, UserDetails userDetails) {
        Optional<Token> findToken = tokenRepositoryMongo.findByToken(token);

        return getClaimsFromToken(token).getExpiration().after(Date.from(Instant.now()))
        && userDetails.getUsername().equals(getClaimsFromToken(token).getSubject());
    }
}
