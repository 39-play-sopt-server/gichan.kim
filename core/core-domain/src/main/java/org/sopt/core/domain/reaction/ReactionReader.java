package org.sopt.core.domain.reaction;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ReactionReader {

    private final ReactionRepository reactionRepository;

    public ReactionReader(ReactionRepository reactionRepository) {
        this.reactionRepository = reactionRepository;
    }

    public Optional<Reaction> find(NewReaction newReaction) {
        return reactionRepository.find(newReaction.targetType(), newReaction.targetId(), newReaction.memberId(), newReaction.type());
    }

    public List<Long> readLikedTargetIds(ReactionTargetType targetType, Long memberId) {
        return reactionRepository.findActiveTargetIds(targetType, memberId, ReactionType.LIKE);
    }
}
