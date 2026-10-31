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
        reactionRepository.updateStatus(id, ReactionStatus.ACTIVE)
                .orElseThrow(() -> new CoreException(ReactionErrorCode.LIKE_NOT_FOUND));
    }

    public void unlike(Long id) {
        reactionRepository.updateStatus(id, ReactionStatus.INACTIVE)
                .orElseThrow(() -> new CoreException(ReactionErrorCode.LIKE_NOT_FOUND));
    }
}
