package org.sopt.core.domain.reaction;

public class Reaction {
    private final Long id;
    private final ReactionTargetType targetType;
    private final Long targetId;
    private final Long memberId;
    private final ReactionType type;
    private final ReactionStatus status;

    private Reaction(Long id, ReactionTargetType targetType, Long targetId, Long memberId, ReactionType type, ReactionStatus status) {
        this.id = id;
        this.targetType = targetType;
        this.targetId = targetId;
        this.memberId = memberId;
        this.type = type;
        this.status = status;
    }

    public static Reaction create(Long id, ReactionTargetType targetType, Long targetId, Long memberId, ReactionType type, ReactionStatus status) {
        return new Reaction(id, targetType, targetId, memberId, type, status);
    }

    public boolean isActive() {
        return status == ReactionStatus.ACTIVE;
    }

    public Long getId() {
        return id;
    }

    public ReactionTargetType getTargetType() {
        return targetType;
    }

    public Long getTargetId() {
        return targetId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public ReactionType getType() {
        return type;
    }

    public ReactionStatus getStatus() {
        return status;
    }
}
