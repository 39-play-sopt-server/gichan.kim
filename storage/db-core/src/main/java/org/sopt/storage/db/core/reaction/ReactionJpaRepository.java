package org.sopt.storage.db.core.reaction;

import org.sopt.core.domain.reaction.ReactionStatus;
import org.sopt.core.domain.reaction.ReactionTargetType;
import org.sopt.core.domain.reaction.ReactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReactionJpaRepository extends JpaRepository<ReactionEntity, Long> {

    Optional<ReactionEntity> findByTargetTypeAndTargetIdAndMemberIdAndReactionType(ReactionTargetType targetType, Long targetId, Long memberId, ReactionType reactionType);

    @Query("select r.targetId from ReactionEntity r where r.targetType = :targetType and r.memberId = :memberId and r.reactionType = :reactionType and r.status = :status")
    List<Long> findTargetIds(@Param("targetType") ReactionTargetType targetType, @Param("memberId") Long memberId, @Param("reactionType") ReactionType reactionType, @Param("status") ReactionStatus status);

    @Modifying(clearAutomatically = true)
    @Query("delete from ReactionEntity r where r.memberId = :memberId")
    int deleteAllByMemberId(@Param("memberId") Long memberId);

    @Modifying(clearAutomatically = true)
    @Query("delete from ReactionEntity r where r.targetType = :targetType and r.targetId in :targetIds")
    int deleteAllByTargetTypeAndTargetIdIn(@Param("targetType") ReactionTargetType targetType, @Param("targetIds") List<Long> targetIds);
}
