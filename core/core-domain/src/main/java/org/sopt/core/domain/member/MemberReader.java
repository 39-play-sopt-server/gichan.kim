package org.sopt.core.domain.member;

import org.sopt.core.error.CoreException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class MemberReader {

    private final MemberRepository memberRepository;

    public MemberReader(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void validateExists(Long id) {
        if (!memberRepository.existsById(id)) {
            throw new CoreException(MemberErrorCode.MEMBER_NOT_FOUND);
        }
    }

    public Map<Long, String> readNamesByIds(List<Long> ids) {
        if (ids.isEmpty()) {
            return Map.of();
        }
        Map<Long, String> names = memberRepository.findAllByIdIn(ids).stream()
                .collect(Collectors.toMap(Member::getId, Member::getName, (a, b) -> a));
        if (names.size() < ids.size()) {
            throw new CoreException(MemberErrorCode.MEMBER_NOT_FOUND);
        }
        return names;
    }
}
