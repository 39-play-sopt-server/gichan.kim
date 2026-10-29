package org.sopt.core.domain.reaction;

import org.springframework.stereotype.Component;

@Component
public class ReactionAppender {

    private final ReactionRepository reactionRepository;

    public ReactionAppender(ReactionRepository reactionRepository) {
        this.reactionRepository = reactionRepository;
    }

    public Reaction append(NewReaction newReaction) {
        return reactionRepository.save(newReaction);
    }
}
