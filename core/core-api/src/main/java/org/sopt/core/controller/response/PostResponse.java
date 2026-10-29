package org.sopt.core.controller.response;

import org.sopt.core.domain.post.Post;
import org.sopt.core.domain.post.PostCategory;

import java.time.LocalDateTime;

public record PostResponse(Long id, String title, String content, PostCategory category, long likeCount, long viewCount, Long memberId, String memberName, LocalDateTime createdAt, LocalDateTime updatedAt) {

    public static PostResponse from(Post post) {
        return new PostResponse(post.getId(), post.getTitle(), post.getContent(), post.getCategory(), post.getLikeCount(), post.getViewCount(), post.getMemberId(), post.getMemberName(), post.getCreatedAt(), post.getUpdatedAt());
    }
}
