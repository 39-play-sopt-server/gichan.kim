package org.sopt.storage.db.core.member;

import org.sopt.core.domain.member.Member;
import org.sopt.core.domain.member.MemberRepository;
import org.sopt.core.domain.member.NewMember;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class MemberCoreRepository implements MemberRepository {
    private final MemberJpaRepository memberJpaRepository;

    public MemberCoreRepository(MemberJpaRepository memberJpaRepository) {
        this.memberJpaRepository = memberJpaRepository;
    }

    @Override
    public Member save(NewMember member) {
        MemberEntity saved = memberJpaRepository.save(MemberEntity.of(member));
        return saved.toDomain();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Member> findAllByIdIn(List<Long> ids) {
        return memberJpaRepository.findAllById(ids).stream().map(MemberEntity::toDomain).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return memberJpaRepository.existsById(id);
    }

    @Override
    public void deleteById(Long id) {
        memberJpaRepository.deleteById(id);
    }
}
