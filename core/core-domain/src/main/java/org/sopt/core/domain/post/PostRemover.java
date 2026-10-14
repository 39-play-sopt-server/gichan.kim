package org.sopt.core.domain.post;

import org.springframework.stereotype.Component;

@Component
public class PostRemover {

    private final PostRepository postRepository;
    private final PostReader postReader;

    public PostRemover(PostRepository postRepository, PostReader postReader) {
        this.postRepository = postRepository;
        this.postReader = postReader;
    }

    public void remove(Long id) {
        postReader.read(id);
        postRepository.deleteById(id);
    }
}
