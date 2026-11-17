package org.sopt.core.controller;

import jakarta.validation.Valid;
import org.sopt.core.controller.request.LoginRequest;
import org.sopt.core.controller.request.SignupRequest;
import org.sopt.core.domain.auth.AuthErrorCode;
import org.sopt.core.domain.auth.AuthService;
import org.sopt.core.domain.auth.TokenPair;
import org.sopt.core.domain.member.MemberErrorCode;
import org.sopt.core.support.auth.RefreshTokenCookieProvider;
import org.sopt.core.support.code.AuthSuccessCode;
import org.sopt.core.support.code.CommonErrorCode;
import org.sopt.core.support.response.SuccessResponse;
import org.sopt.core.support.swagger.ApiException;
import org.sopt.core.support.swagger.ApiSuccess;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/auth")
public class AuthController {
    private final AuthService authService;
    private final RefreshTokenCookieProvider refreshTokenCookieProvider;

    public AuthController(AuthService authService, RefreshTokenCookieProvider refreshTokenCookieProvider) {
        this.authService = authService;
        this.refreshTokenCookieProvider = refreshTokenCookieProvider;
    }

    @ApiSuccess(value = AuthSuccessCode.class, codes = "SIGNED_UP")
    @ApiException(value = MemberErrorCode.class, codes = "EMAIL_ALREADY_EXISTS")
    @ApiException(value = CommonErrorCode.class, codes = "INVALID_INPUT")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/signup")
    public SuccessResponse<Long> signup(
            @Valid @RequestBody(required = true) SignupRequest request
    ) {
        Long memberId = authService.signup(request.name(), request.email(), request.password());
        return SuccessResponse.of(AuthSuccessCode.SIGNED_UP, memberId);
    }

    @ApiSuccess(value = AuthSuccessCode.class, codes = "LOGGED_IN")
    @ApiException(value = AuthErrorCode.class, codes = "INVALID_CREDENTIALS")
    @ApiException(value = CommonErrorCode.class, codes = "INVALID_INPUT")
    @PostMapping(path = "/login")
    public ResponseEntity<SuccessResponse<Void>> login(
            @Valid @RequestBody(required = true) LoginRequest request
    ) {
        TokenPair tokenPair = authService.login(request.email(), request.password());
        return ResponseEntity.ok()
                .headers(headers -> headers.setBearerAuth(tokenPair.accessToken()))
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookieProvider.create(tokenPair.refreshToken()).toString())
                .body(SuccessResponse.of(AuthSuccessCode.LOGGED_IN));
    }

    @ApiSuccess(value = AuthSuccessCode.class, codes = "TOKEN_REISSUED")
    @ApiException(value = AuthErrorCode.class, codes = "INVALID_TOKEN")
    @ApiException(value = MemberErrorCode.class, codes = "MEMBER_NOT_FOUND")
    @PostMapping(path = "/reissue")
    public ResponseEntity<SuccessResponse<Void>> reissue(
            @CookieValue(name = RefreshTokenCookieProvider.COOKIE_NAME, required = false) String refreshToken
    ) {
        String accessToken = authService.reissue(refreshToken);
        return ResponseEntity.ok()
                .headers(headers -> headers.setBearerAuth(accessToken))
                .body(SuccessResponse.of(AuthSuccessCode.TOKEN_REISSUED));
    }
}
