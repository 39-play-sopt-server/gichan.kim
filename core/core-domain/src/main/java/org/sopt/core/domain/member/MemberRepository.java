package org.sopt.core.domain.member;

import java.util.List;

public interface MemberRepository {
    Member save(NewMember member);

    List<Member> findAllByIdIn(List<Long> ids);

    boolean existsById(Long id);

    void deleteById(Long id);
}
