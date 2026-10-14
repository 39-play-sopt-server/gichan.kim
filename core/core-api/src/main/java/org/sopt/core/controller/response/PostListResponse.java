package org.sopt.core.controller.response;

import org.sopt.core.domain.post.Post;

import java.util.List;

public record PostListResponse(List<PostResponse> posts){
    public static PostListResponse from(List<Post> posts){
        return new PostListResponse(
                posts.stream()
                        .map(PostResponse::from)
                        .toList()
        );
    }
}
