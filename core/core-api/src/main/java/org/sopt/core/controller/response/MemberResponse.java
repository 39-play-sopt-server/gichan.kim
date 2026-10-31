package org.sopt.core.controller.response;

import org.sopt.core.domain.member.Member;

import java.time.LocalDateTime;

public record MemberResponse(Long id, String name, String email, LocalDateTime createdAt, LocalDateTime updatedAt) {

    public static MemberResponse from(Member member) {
        return new MemberResponse(member.getId(), member.getName(), member.getEmail(), member.getCreatedAt(), member.getUpdatedAt());
    }
}
