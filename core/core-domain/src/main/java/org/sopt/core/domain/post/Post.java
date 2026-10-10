package org.sopt.core.domain.post;

public class Post {

    private String title;
    private String content;
    private PostCategory category;
    private final long likeCount;

    public Post(String title, String content, PostCategory category, long likeCount) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.likeCount = likeCount;
    }

    public void update(String title, String content, PostCategory category) {
        this.title = title;
        this.content = content;
        this.category = category;
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
