package org.sopt.storage.db.core.post;

import jakarta.persistence.*;
import org.sopt.core.domain.post.NewPost;
import org.sopt.core.domain.post.Post;
import org.sopt.core.domain.post.PostCategory;

@Entity
@Table(name = "post")
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    @Enumerated(EnumType.STRING)
    private PostCategory category;
    private long likeCount;
    private long viewCount;

    protected PostEntity() {}

    private PostEntity(Long id, String title, String content, PostCategory category, long likeCount, long viewCount) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.category = category;
        this.likeCount = likeCount;
        this.viewCount = viewCount;
    }

    public static PostEntity of(Long id, NewPost newPost) {
        return new PostEntity(id, newPost.title(), newPost.content(), newPost.category(), 0L, 0L);
    }

    public Post toDomain() {
        return Post.create(id, title, content, category, likeCount, viewCount);
    }

    public PostEntity updated(String title, String content, PostCategory category) {
        return new PostEntity(id, title, content, category, likeCount, viewCount);
    }

    public PostEntity viewed() {
        return new PostEntity(id, title, content, category, likeCount, viewCount + 1);
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public PostCategory getCategory() {
        return category;
    }

    public long getLikeCount() {
        return likeCount;
    }
}
