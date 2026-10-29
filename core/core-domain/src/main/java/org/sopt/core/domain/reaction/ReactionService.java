package org.sopt.core.domain.reaction;

import org.sopt.core.error.CoreException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReactionService {

    private final ReactionReader reactionReader;
    private final ReactionAppender reactionAppender;
    private final ReactionUpdater reactionUpdater;

    public ReactionService(ReactionReader reactionReader, ReactionAppender reactionAppender, ReactionUpdater reactionUpdater) {
        this.reactionReader = reactionReader;
        this.reactionAppender = reactionAppender;
        this.reactionUpdater = reactionUpdater;
    }

    public void like(ReactionTargetType targetType, Long targetId, Long memberId) {
        NewReaction newReaction = new NewReaction(targetType, targetId, memberId, ReactionType.LIKE);
        Optional<Reaction> existing = reactionReader.find(newReaction);
        if (existing.isEmpty()) {
            reactionAppender.append(newReaction);
            return;
        }
        Reaction reaction = existing.get();
        if (reaction.isActive()) {
            throw new CoreException(ReactionErrorCode.LIKE_ALREADY_EXISTS);
        }
        reactionUpdater.like(reaction.getId());
    }

    public void unlike(ReactionTargetType targetType, Long targetId, Long memberId) {
        NewReaction newReaction = new NewReaction(targetType, targetId, memberId, ReactionType.LIKE);
        Reaction reaction = reactionReader.find(newReaction)
                .filter(Reaction::isActive)
                .orElseThrow(() -> new CoreException(ReactionErrorCode.LIKE_NOT_FOUND));
        reactionUpdater.unlike(reaction.getId());
    }
}
