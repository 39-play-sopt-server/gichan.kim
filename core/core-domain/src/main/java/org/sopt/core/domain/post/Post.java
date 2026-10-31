package org.sopt.core.domain.post;

import java.time.LocalDateTime;

public class Post {
    private final Long id;
    private final String title;
    private final String content;
    private final PostCategory category;
    private final long likeCount;
    private final long viewCount;
    private final Long memberId;
    private final String memberName;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    private Post(Long id, String title, String content, PostCategory category, long likeCount, long viewCount, Long memberId, String memberName, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.category = category;
        this.likeCount = likeCount;
        this.viewCount = viewCount;
        this.memberId = memberId;
        this.memberName = memberName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Post create(Long id, String title, String content, PostCategory category, long likeCount, long viewCount, Long memberId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new Post(id, title, content, category, likeCount, viewCount, memberId, null, createdAt, updatedAt);
    }

    public Post withMemberName(String memberName) {
        return new Post(id, title, content, category, likeCount, viewCount, memberId, memberName, createdAt, updatedAt);
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

    public long getViewCount() {
        return viewCount;
    }

    public Long getMemberId() {
        return memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
