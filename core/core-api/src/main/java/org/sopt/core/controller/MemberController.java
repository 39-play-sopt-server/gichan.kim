package org.sopt.core.controller;

import org.sopt.core.domain.member.MemberErrorCode;
import org.sopt.core.domain.member.MemberService;
import org.sopt.core.support.auth.RefreshTokenCookieProvider;
import org.sopt.core.support.code.MemberSuccessCode;
import org.sopt.core.support.response.SuccessResponse;
import org.sopt.core.support.swagger.ApiException;
import org.sopt.core.support.swagger.ApiSuccess;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/members")
public class MemberController {
    private final MemberService memberService;
    private final RefreshTokenCookieProvider refreshTokenCookieProvider;

    public MemberController(MemberService memberService, RefreshTokenCookieProvider refreshTokenCookieProvider) {
        this.memberService = memberService;
        this.refreshTokenCookieProvider = refreshTokenCookieProvider;
    }

    @ApiSuccess(value = MemberSuccessCode.class, codes = "MEMBER_ME_READ")
    @GetMapping(path = "/me")
    public SuccessResponse<Long> readMe(
            @AuthenticationPrincipal Long memberId
    ) {
        return SuccessResponse.of(MemberSuccessCode.MEMBER_ME_READ, memberId);
    }

    @ApiSuccess(value = MemberSuccessCode.class, codes = "MEMBER_DELETED")
    @ApiException(value = MemberErrorCode.class, codes = "MEMBER_NOT_FOUND")
    @DeleteMapping(path = "/me")
    public ResponseEntity<SuccessResponse<Void>> withdraw(
            @AuthenticationPrincipal Long memberId
    ) {
        memberService.remove(memberId);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookieProvider.expire().toString())
                .body(SuccessResponse.of(MemberSuccessCode.MEMBER_DELETED));
    }
}
