package org.sopt.post.domain;

public class Post {
    String title;
    String content;

    protected Post(){

    }

    private Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public static Post create(String title, String content){
        return new Post(title, content);
    }

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return this.title;
    }

    public String getContent() {
        return this.content;
    }
}
