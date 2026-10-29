package org.sopt.core.controller;

import jakarta.validation.Valid;
import org.sopt.core.controller.request.CreateMemberRequest;
import org.sopt.core.controller.response.MemberResponse;
import org.sopt.core.domain.member.Member;
import org.sopt.core.domain.member.MemberErrorCode;
import org.sopt.core.domain.member.MemberService;
import org.sopt.core.support.code.CommonErrorCode;
import org.sopt.core.support.code.MemberSuccessCode;
import org.sopt.core.support.response.SuccessResponse;
import org.sopt.core.support.swagger.ApiException;
import org.sopt.core.support.swagger.ApiSuccess;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/members")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @ApiSuccess(value = MemberSuccessCode.class, codes = "MEMBER_CREATED")
    @ApiException(value = CommonErrorCode.class, codes = "INVALID_INPUT")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public SuccessResponse<MemberResponse> createMember(
            @Valid @RequestBody(required = true) CreateMemberRequest request
    ) {
        Member member = memberService.write(request.toNewMember());
        return SuccessResponse.of(MemberSuccessCode.MEMBER_CREATED, MemberResponse.from(member));
    }

    @ApiSuccess(value = MemberSuccessCode.class, codes = "MEMBER_DELETED")
    @ApiException(value = MemberErrorCode.class, codes = "MEMBER_NOT_FOUND")
    @ApiException(value = CommonErrorCode.class, codes = "INVALID_INPUT")
    @DeleteMapping(path = "/{memberId}")
    public SuccessResponse<Void> deleteMember(
            @PathVariable(name = "memberId") Long memberId
    ) {
        memberService.remove(memberId);
        return SuccessResponse.of(MemberSuccessCode.MEMBER_DELETED);
    }
}
