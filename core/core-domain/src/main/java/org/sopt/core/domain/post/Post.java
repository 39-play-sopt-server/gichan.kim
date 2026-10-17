package org.sopt.core.domain.post;

public class Post {
    private final Long id;
    private final String title;
    private final String content;
    private final PostCategory category;
    private final long likeCount;
    private final long viewCount;

    private Post(Long id, String title, String content, PostCategory category, long likeCount, long viewCount) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.category = category;
        this.likeCount = likeCount;
        this.viewCount = viewCount;
    }

    public static Post create(Long id, String title, String content, PostCategory category, long likeCount, long viewCount) {
        return new Post(id, title, content, category, likeCount, viewCount);
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
}
