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
    private final long viewCount;

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

    // 상태 변경은 저장되는 쪽에서. 필드가 final이라 바뀐 값을 가진 새 객체를 돌려준다
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
