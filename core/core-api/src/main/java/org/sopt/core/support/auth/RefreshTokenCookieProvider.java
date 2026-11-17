package org.sopt.core.support.auth;

import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class RefreshTokenCookieProvider {

    public static final String COOKIE_NAME = "refreshToken";
    private static final String COOKIE_PATH = "/api/v1/auth";
    private static final String SAME_SITE = "Strict";

    private final Duration refreshTokenMaxAge;

    public RefreshTokenCookieProvider(JwtProperties jwtProperties) {
        this.refreshTokenMaxAge = jwtProperties.refreshTokenExpiration();
    }

    public ResponseCookie create(String refreshToken) {
        return cookie(refreshToken, refreshTokenMaxAge);
    }

    public ResponseCookie expire() {
        return cookie("", Duration.ZERO);
    }

    private ResponseCookie cookie(String value, Duration maxAge) {
        return ResponseCookie.from(COOKIE_NAME, value)
                .httpOnly(true)
                .secure(true)
                .sameSite(SAME_SITE)
                .path(COOKIE_PATH)
                .maxAge(maxAge)
                .build();
    }
}
