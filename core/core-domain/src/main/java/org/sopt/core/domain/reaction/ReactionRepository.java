package org.sopt.core.domain.reaction;

import java.util.List;
import java.util.Optional;

public interface ReactionRepository {

    Reaction save(NewReaction newReaction);

    Optional<Reaction> find(ReactionTargetType targetType, Long targetId, Long memberId, ReactionType type);

    List<Long> findActiveTargetIds(ReactionTargetType targetType, Long memberId, ReactionType type);

    Optional<Reaction> updateStatus(Long id, ReactionStatus status);

    void deleteAllByMemberId(Long memberId);

    void deleteAllByTargets(ReactionTargetType targetType, List<Long> targetIds);
}
