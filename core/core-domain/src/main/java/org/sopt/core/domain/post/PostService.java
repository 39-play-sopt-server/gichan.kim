package org.sopt.core.domain.post;

import org.sopt.core.domain.member.MemberReader;
import org.sopt.core.domain.reaction.ReactionRemover;
import org.sopt.core.domain.reaction.ReactionService;
import org.sopt.core.domain.reaction.ReactionTargetType;
import org.sopt.core.support.PageResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class PostService {

    private final PostReader postReader;
    private final PostAppender postAppender;
    private final PostUpdater postUpdater;
    private final PostRemover postRemover;
    private final MemberReader memberReader;
    private final ReactionService reactionService;
    private final ReactionRemover reactionRemover;

    public PostService(PostReader postReader, PostAppender postAppender, PostUpdater postUpdater, PostRemover postRemover, MemberReader memberReader, ReactionService reactionService, ReactionRemover reactionRemover) {
        this.postReader = postReader;
        this.postAppender = postAppender;
        this.postUpdater = postUpdater;
        this.postRemover = postRemover;
        this.memberReader = memberReader;
        this.reactionService = reactionService;
        this.reactionRemover = reactionRemover;
    }

    public Post write(NewPost newPost) {
        return withMemberName(postAppender.append(newPost));
    }

    public PageResult<Post> readAll(PostCategory category, String keyword, int page, int size) {
        PageResult<Post> posts = postReader.readAll(category, keyword, page, size);
        Map<Long, String> memberNames = memberReader.readNamesByIds(memberIdsOf(posts.content()));
        return posts.map(post -> post.withMemberName(memberNames.get(post.getMemberId())));
    }

    public Post read(Long id) {
        return withMemberName(postUpdater.increaseViewCount(id));
    }

    public Post edit(Long id, String title, String content, PostCategory category) {
        return withMemberName(postUpdater.update(id, title, content, category));
    }

    @Transactional
    public void remove(Long id) {
        postRemover.remove(id);
        reactionRemover.removeAllByTargets(ReactionTargetType.POST, List.of(id));
    }

    @Transactional
    public void like(Long id, Long memberId) {
        postReader.validateExists(id);
        reactionService.like(ReactionTargetType.POST, id, memberId);
        postUpdater.increaseLikeCount(id);
    }

    @Transactional
    public void unlike(Long id, Long memberId) {
        postReader.validateExists(id);
        reactionService.unlike(ReactionTargetType.POST, id, memberId);
        postUpdater.decreaseLikeCount(id);
    }

    private Post withMemberName(Post post) {
        Map<Long, String> memberNames = memberReader.readNamesByIds(List.of(post.getMemberId()));
        return post.withMemberName(memberNames.get(post.getMemberId()));
    }

    private List<Long> memberIdsOf(List<Post> posts) {
        return posts.stream().map(Post::getMemberId).distinct().toList();
    }
}
