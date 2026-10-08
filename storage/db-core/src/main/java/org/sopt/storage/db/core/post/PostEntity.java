package org.sopt.storage.db.core.post;

import org.sopt.core.domain.post.NewPost;
import org.sopt.core.domain.post.Post;
import org.sopt.core.domain.post.PostCategory;

public class PostEntity {

    private final String title;
    private final String content;
    private final PostCategory category;
    private final long likeCount;

    public PostEntity(String title, String content, PostCategory category, long likeCount) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.likeCount = likeCount;
    }

    public static PostEntity of(NewPost newPost) {
        return new PostEntity(newPost.title(), newPost.content(), newPost.category(), 0L);
    }

    public static PostEntity from(Post post) {
        return new PostEntity(post.getTitle(), post.getContent(), post.getCategory(), post.getLikeCount());
    }

    public Post toDomain() {
        return new Post(title, content, category, likeCount);
    }
}
