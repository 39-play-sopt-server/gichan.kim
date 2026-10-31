package org.sopt.core.domain.member;

import org.sopt.core.error.CoreException;
import org.springframework.stereotype.Component;

@Component
public class MemberRemover {

    private final MemberRepository memberRepository;

    public MemberRemover(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void remove(Long id) {
        if (!memberRepository.existsById(id)) {
            throw new CoreException(MemberErrorCode.MEMBER_NOT_FOUND);
        }
        memberRepository.deleteById(id);
    }
}
