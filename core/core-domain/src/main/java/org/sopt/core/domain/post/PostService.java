package org.sopt.core.domain.post;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
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

    public Post read(Long index) {
        return postReader.read(index);
    }

    public Post edit(Long id, String title, String content, PostCategory category) {
        return postUpdater.update(id, title, content, category);
    }

    public void remove(Long id) {
        postRemover.remove(id);
    }
}
