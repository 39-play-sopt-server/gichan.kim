package org.sopt.storage.db.core.reaction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import org.sopt.core.domain.reaction.NewReaction;
import org.sopt.core.domain.reaction.Reaction;
import org.sopt.core.domain.reaction.ReactionStatus;
import org.sopt.core.domain.reaction.ReactionTargetType;
import org.sopt.core.domain.reaction.ReactionType;
import org.sopt.storage.db.core.BaseEntity;

@Entity
@Table(
        name = "reaction",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_reaction_target_member_type",
                columnNames = {"target_type", "target_id", "member_id", "reaction_type"}
        )
)
public class ReactionEntity extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ReactionTargetType targetType;

    @Column(nullable = false)
    private Long targetId;

    @Column(nullable = false)
    private Long memberId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ReactionType reactionType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ReactionStatus status;

    protected ReactionEntity() {}

    private ReactionEntity(ReactionTargetType targetType, Long targetId, Long memberId, ReactionType reactionType, ReactionStatus status) {
        this.targetType = targetType;
        this.targetId = targetId;
        this.memberId = memberId;
        this.reactionType = reactionType;
        this.status = status;
    }

    public static ReactionEntity of(NewReaction newReaction) {
        return new ReactionEntity(newReaction.targetType(), newReaction.targetId(), newReaction.memberId(), newReaction.type(), ReactionStatus.ACTIVE);
    }

    public Reaction toDomain() {
        return Reaction.create(getId(), targetType, targetId, memberId, reactionType, status);
    }

    public void changeStatus(ReactionStatus status) {
        this.status = status;
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

    public ReactionType getReactionType() {
        return reactionType;
    }

    public ReactionStatus getStatus() {
        return status;
    }
}
