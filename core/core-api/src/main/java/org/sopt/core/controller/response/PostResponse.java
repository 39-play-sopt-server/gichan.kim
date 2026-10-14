package org.sopt.core.controller.response;

import org.sopt.core.domain.post.Post;
import org.sopt.core.domain.post.PostCategory;

public record PostResponse(Long id, String title, String content, PostCategory category, long likeCount) {

    public static PostResponse from(Post post) {
        return new PostResponse(post.getId(), post.getTitle(), post.getContent(), post.getCategory(), post.getLikeCount());
    }
}
