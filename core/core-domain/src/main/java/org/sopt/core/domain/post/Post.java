package org.sopt.core.domain.post;

public class Post {
    private final Long id;
    private String title;
    private String content;
    private PostCategory category;
    private final long likeCount;

    private Post(Long id, String title, String content, PostCategory category, long likeCount) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.category = category;
        this.likeCount = likeCount;
    }

    public static Post create(Long id, String title, String content, PostCategory category, long likeCount){
        return new Post(id, title, content, category, likeCount);
    }

    public void update(String title, String content, PostCategory category) {
        this.title = title;
        this.content = content;
        this.category = category;
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
