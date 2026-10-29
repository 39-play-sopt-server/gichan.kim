package org.sopt.core.domain.reaction;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReactionRemover {

    private final ReactionRepository reactionRepository;

    public ReactionRemover(ReactionRepository reactionRepository) {
        this.reactionRepository = reactionRepository;
    }

    public void removeAllByMemberId(Long memberId) {
        reactionRepository.deleteAllByMemberId(memberId);
    }

    public void removeAllByTargets(ReactionTargetType targetType, List<Long> targetIds) {
        if (targetIds.isEmpty()) {
            return;
        }
        reactionRepository.deleteAllByTargets(targetType, targetIds);
    }
}
