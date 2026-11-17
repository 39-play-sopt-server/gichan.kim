package org.sopt.core.support.auth;

import io.jsonwebtoken.Jwts;
import org.sopt.core.domain.auth.TokenIssuer;
import org.sopt.core.domain.auth.TokenType;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;

@Component
public class JwtTokenIssuer implements TokenIssuer {

    private final SecretKey secretKey;
    private final JwtProperties jwtProperties;

    public JwtTokenIssuer(JwtProperties jwtProperties) {
        this.secretKey = jwtProperties.secretKey();
        this.jwtProperties = jwtProperties;
    }

    @Override
    public String issue(Long memberId, TokenType tokenType) {
        Date now = new Date();

        return Jwts.builder()
                .subject(String.valueOf(memberId))
                .claim(TokenType.CLAIM_KEY, tokenType.name())
                .issuedAt(now)
                .expiration(new Date(now.getTime() + expirationOf(tokenType).toMillis()))
                .signWith(secretKey)
                .compact();
    }

    private Duration expirationOf(TokenType tokenType) {
        return switch (tokenType) {
            case ACCESS -> jwtProperties.accessTokenExpiration();
            case REFRESH -> jwtProperties.refreshTokenExpiration();
        };
    }
}
