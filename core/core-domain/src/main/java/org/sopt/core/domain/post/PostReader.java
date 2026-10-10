package org.sopt.core.domain.post;

import org.sopt.core.error.CoreException;

import java.util.List;

public class PostReader {

    private final PostRepository postRepository;

    public PostReader(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post read(int index) {
        return postRepository.findByIndex(index)
                .orElseThrow(() -> new CoreException(PostErrorCode.POST_NOT_FOUND));
    }

    public List<Post> readAll() {
        return postRepository.findAll();
    }
}
