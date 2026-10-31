package org.sopt.core.controller.request;

import jakarta.validation.constraints.NotBlank;
import org.sopt.core.domain.member.NewMember;

public record CreateMemberRequest(
        @NotBlank(message = "이름은 비어 있을 수 없습니다.") String name,
        @NotBlank(message = "이메일은 비어 있을 수 없습니다.") String email
) {

    public NewMember toNewMember() {
        return new NewMember(name, email);
    }
}
