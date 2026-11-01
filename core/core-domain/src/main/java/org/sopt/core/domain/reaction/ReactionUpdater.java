package org.sopt.core.domain.reaction;

import org.sopt.core.error.CoreException;
import org.springframework.stereotype.Component;

@Component
public class ReactionUpdater {

    private final ReactionRepository reactionRepository;

    public ReactionUpdater(ReactionRepository reactionRepository) {
        this.reactionRepository = reactionRepository;
    }

    public void like(Long id) {
        if (!reactionRepository.updateStatus(id, ReactionStatus.INACTIVE, ReactionStatus.ACTIVE)) {
            throw new CoreException(ReactionErrorCode.LIKE_ALREADY_EXISTS);
        }
    }

    public void unlike(Long id) {
        if (!reactionRepository.updateStatus(id, ReactionStatus.ACTIVE, ReactionStatus.INACTIVE)) {
            throw new CoreException(ReactionErrorCode.LIKE_NOT_FOUND);
        }
    }
}
