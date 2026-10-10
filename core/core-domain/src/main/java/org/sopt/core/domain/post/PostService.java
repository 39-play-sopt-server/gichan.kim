package org.sopt.core.domain.post;

import java.util.List;

public class PostService {

    private final PostReader postReader;
    private final PostAppender postAppender;
    private final PostUpdater postUpdater;
    private final PostRemover postRemover;

    public PostService(PostReader postReader, PostAppender postAppender, PostUpdater postUpdater, PostRemover postRemover) {
        this.postReader = postReader;
        this.postAppender = postAppender;
        this.postUpdater = postUpdater;
        this.postRemover = postRemover;
    }

    public Post write(NewPost newPost) {
        return postAppender.append(newPost);
    }

    public List<Post> readAll() {
        return postReader.readAll();
    }

    public Post read(int index) {
        return postReader.read(index);
    }

    public Post edit(int index, String title, String content, PostCategory category) {
        return postUpdater.update(index, title, content, category);
    }

    public void remove(int index) {
        postRemover.remove(index);
    }
}
