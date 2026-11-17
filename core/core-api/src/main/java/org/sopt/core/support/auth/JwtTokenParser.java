package org.sopt.core.support.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.sopt.core.domain.auth.AuthErrorCode;
import org.sopt.core.domain.auth.TokenParser;
import org.sopt.core.domain.auth.TokenType;
import org.sopt.core.error.CoreException;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JwtTokenParser implements TokenParser {

    private final SecretKey secretKey;

    public JwtTokenParser(JwtProperties jwtProperties) {
        this.secretKey = jwtProperties.secretKey();
    }

    @Override
    public Long parseMemberId(String token, TokenType tokenType) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(secretKey)
                    .require(TokenType.CLAIM_KEY, tokenType.name())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            return Long.valueOf(claims.getSubject());
        } catch (ExpiredJwtException e) {
            throw new CoreException(AuthErrorCode.EXPIRED_TOKEN);
        } catch (JwtException | IllegalArgumentException e) {
            throw new CoreException(AuthErrorCode.INVALID_TOKEN);
        }
    }
}
