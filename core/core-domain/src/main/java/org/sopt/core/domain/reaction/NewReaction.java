package org.sopt.core.domain.reaction;

public record NewReaction(ReactionTargetType targetType, Long targetId, Long memberId, ReactionType type) {
}
