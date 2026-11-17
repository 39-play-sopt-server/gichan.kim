package org.sopt.core.domain.member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(NewMember member);

    List<Member> findAllByIdIn(List<Long> ids);

    Optional<Member> findByEmail(String email);

    boolean existsById(Long id);

    boolean existsByEmail(String email);

    void deleteById(Long id);
}
