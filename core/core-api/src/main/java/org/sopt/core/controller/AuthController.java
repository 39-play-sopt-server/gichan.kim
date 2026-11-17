package org.sopt.core.controller;

import jakarta.validation.Valid;
import org.sopt.core.controller.request.LoginRequest;
import org.sopt.core.controller.request.SignupRequest;
import org.sopt.core.controller.response.LoginResponse;
import org.sopt.core.domain.auth.AuthErrorCode;
import org.sopt.core.domain.auth.AuthService;
import org.sopt.core.domain.member.MemberErrorCode;
import org.sopt.core.support.auth.JwtProvider;
import org.sopt.core.support.code.AuthSuccessCode;
import org.sopt.core.support.code.CommonErrorCode;
import org.sopt.core.support.response.SuccessResponse;
import org.sopt.core.support.swagger.ApiException;
import org.sopt.core.support.swagger.ApiSuccess;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/auth")
public class AuthController {
    private final AuthService authService;
    private final JwtProvider jwtProvider;

    public AuthController(AuthService authService, JwtProvider jwtProvider) {
        this.authService = authService;
        this.jwtProvider = jwtProvider;
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
    public SuccessResponse<LoginResponse> login(
            @Valid @RequestBody(required = true) LoginRequest request
    ) {
        Long memberId = authService.login(request.email(), request.password());
        String accessToken = jwtProvider.createAccessToken(memberId);
        return SuccessResponse.of(AuthSuccessCode.LOGGED_IN, new LoginResponse(accessToken));
    }
}
