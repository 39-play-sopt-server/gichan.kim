package org.sopt.core.domain.member;

import org.sopt.core.domain.post.PostReader;
import org.sopt.core.domain.post.PostRemover;
import org.sopt.core.domain.post.PostUpdater;
import org.sopt.core.domain.reaction.ReactionReader;
import org.sopt.core.domain.reaction.ReactionRemover;
import org.sopt.core.domain.reaction.ReactionTargetType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MemberService {

    private final MemberReader memberReader;
    private final MemberAppender memberAppender;
    private final MemberRemover memberRemover;
    private final PostReader postReader;
    private final PostUpdater postUpdater;
    private final PostRemover postRemover;
    private final ReactionReader reactionReader;
    private final ReactionRemover reactionRemover;

    public MemberService(MemberReader memberReader, MemberAppender memberAppender, MemberRemover memberRemover, PostReader postReader, PostUpdater postUpdater, PostRemover postRemover, ReactionReader reactionReader, ReactionRemover reactionRemover) {
        this.memberReader = memberReader;
        this.memberAppender = memberAppender;
        this.memberRemover = memberRemover;
        this.postReader = postReader;
        this.postUpdater = postUpdater;
        this.postRemover = postRemover;
        this.reactionReader = reactionReader;
        this.reactionRemover = reactionRemover;
    }

    public Member write(NewMember newMember) {
        return memberAppender.append(newMember);
    }

    @Transactional
    public void remove(Long id) {
        memberReader.validateExists(id);
        List<Long> likedPostIds = reactionReader.readLikedTargetIds(ReactionTargetType.POST, id);
        postUpdater.decreaseLikeCountAll(likedPostIds);
        List<Long> postIds = postReader.readIdsByMemberId(id);
        reactionRemover.removeAllByTargets(ReactionTargetType.POST, postIds);
        reactionRemover.removeAllByMemberId(id);
        postRemover.removeAllByMemberId(id);
        memberRemover.remove(id);
    }
}
