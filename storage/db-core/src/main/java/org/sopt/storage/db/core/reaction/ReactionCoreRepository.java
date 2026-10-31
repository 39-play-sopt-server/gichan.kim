package org.sopt.storage.db.core.reaction;

import org.sopt.core.domain.reaction.NewReaction;
import org.sopt.core.domain.reaction.Reaction;
import org.sopt.core.domain.reaction.ReactionRepository;
import org.sopt.core.domain.reaction.ReactionStatus;
import org.sopt.core.domain.reaction.ReactionTargetType;
import org.sopt.core.domain.reaction.ReactionType;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class ReactionCoreRepository implements ReactionRepository {

    private final ReactionJpaRepository reactionJpaRepository;

    public ReactionCoreRepository(ReactionJpaRepository reactionJpaRepository) {
        this.reactionJpaRepository = reactionJpaRepository;
    }

    @Override
    public Reaction save(NewReaction newReaction) {
        ReactionEntity saved = reactionJpaRepository.save(ReactionEntity.of(newReaction));
        return saved.toDomain();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Reaction> find(ReactionTargetType targetType, Long targetId, Long memberId, ReactionType type) {
        return reactionJpaRepository.findByTargetTypeAndTargetIdAndMemberIdAndReactionType(targetType, targetId, memberId, type)
                .map(ReactionEntity::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Long> findActiveTargetIds(ReactionTargetType targetType, Long memberId, ReactionType type) {
        return reactionJpaRepository.findTargetIds(targetType, memberId, type, ReactionStatus.ACTIVE);
    }

    @Override
    public Optional<Reaction> updateStatus(Long id, ReactionStatus status) {
        return reactionJpaRepository.findById(id)
                .map(entity -> {
                    entity.changeStatus(status);
                    return entity.toDomain();
                });
    }

    @Override
    public void deleteAllByMemberId(Long memberId) {
        reactionJpaRepository.deleteAllByMemberId(memberId);
    }

    @Override
    public void deleteAllByTargets(ReactionTargetType targetType, List<Long> targetIds) {
        reactionJpaRepository.deleteAllByTargetTypeAndTargetIdIn(targetType, targetIds);
    }
}
