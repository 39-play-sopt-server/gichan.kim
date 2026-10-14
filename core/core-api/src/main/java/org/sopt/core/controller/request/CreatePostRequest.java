package org.sopt.core.controller.request;

import org.sopt.core.domain.post.NewPost;
import org.sopt.core.domain.post.PostCategory;

public record CreatePostRequest(String title, String content, PostCategory category) {

    public NewPost toNewPost() {
        return new NewPost(title, content, category);
    }
}
