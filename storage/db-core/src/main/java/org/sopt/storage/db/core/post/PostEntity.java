package org.sopt.storage.db.core.post;

import jakarta.persistence.*;
import org.sopt.core.domain.post.NewPost;
import org.sopt.core.domain.post.Post;
import org.sopt.core.domain.post.PostCategory;
import org.sopt.storage.db.core.BaseEntity;

@Entity
@Table(name = "post")
public class PostEntity extends BaseEntity {

    private String title;
    private String content;
    @Enumerated(EnumType.STRING)
    private PostCategory category;
    private long likeCount;
    private long viewCount;
    private Long memberId;

    protected PostEntity() {}

    private PostEntity(String title, String content, PostCategory category, long likeCount, long viewCount, Long memberId) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.likeCount = likeCount;
        this.viewCount = viewCount;
        this.memberId = memberId;
    }

    public static PostEntity of(NewPost newPost) {
        return new PostEntity(newPost.title(), newPost.content(), newPost.category(), 0L, 0L, newPost.memberId());
    }

    public Post toDomain() {
        return Post.create(getId(), title, content, category, likeCount, viewCount, memberId, getCreatedAt(), getUpdatedAt());
    }

    public void update(String title, String content, PostCategory category) {
        if (title != null) {
            this.title = title;
        }
        if (content != null) {
            this.content = content;
        }
        if (category != null) {
            this.category = category;
        }
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

    public long getViewCount() {
        return viewCount;
    }

    public Long getMemberId() {
        return memberId;
    }
}
