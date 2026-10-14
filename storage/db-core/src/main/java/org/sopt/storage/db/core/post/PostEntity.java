package org.sopt.storage.db.core.post;

import org.sopt.core.domain.post.NewPost;
import org.sopt.core.domain.post.Post;
import org.sopt.core.domain.post.PostCategory;

public class PostEntity {

    private final Long id;
    private final String title;
    private final String content;
    private final PostCategory category;
    private final long likeCount;

    private PostEntity(Long id, String title, String content, PostCategory category, long likeCount) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.category = category;
        this.likeCount = likeCount;
    }

    public static PostEntity of(Long id, NewPost newPost) {
        return new PostEntity(id, newPost.title(), newPost.content(), newPost.category(), 0L);
    }

    public static PostEntity from(Post post) {
        return new PostEntity(post.getId(), post.getTitle(), post.getContent(), post.getCategory(), post.getLikeCount());
    }

    public Post toDomain() {
        return Post.create(id, title, content, category, likeCount);
    }

    public Long getId() {
        return id;
    }
}
