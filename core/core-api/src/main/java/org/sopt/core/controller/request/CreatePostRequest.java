package org.sopt.core.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.sopt.core.domain.post.NewPost;
import org.sopt.core.domain.post.PostCategory;

public record CreatePostRequest(
        @NotBlank(message = "제목은 비어 있을 수 없습니다.") String title,
        @NotBlank(message = "본문은 비어 있을 수 없습니다.") String content,
        @NotNull(message = "게시글 종류는 필수입니다.") PostCategory category
) {

    public NewPost toNewPost(Long memberId) {
        return new NewPost(title, content, category, memberId);
    }
}
