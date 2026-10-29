package org.sopt.core.domain.member;

import org.springframework.stereotype.Component;

@Component
public class MemberAppender {

    private final MemberRepository memberRepository;

    public MemberAppender(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member append(NewMember newMember) {
        return memberRepository.save(newMember);
    }
}
