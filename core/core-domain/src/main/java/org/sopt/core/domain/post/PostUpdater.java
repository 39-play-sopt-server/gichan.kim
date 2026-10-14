package org.sopt.core.domain.post;

import org.springframework.stereotype.Component;

@Component
public class PostUpdater {

    private final PostRepository postRepository;
    private final PostReader postReader;
    private final PostValidator postValidator;

    public PostUpdater(PostRepository postRepository, PostReader postReader, PostValidator postValidator) {
        this.postRepository = postRepository;
        this.postReader = postReader;
        this.postValidator = postValidator;
    }

    public Post update(Long id, String title, String content, PostCategory category) {
        Post post = postReader.read(id);
        postValidator.validate(title, content);
        post.update(title, content, category);
        return postRepository.update(id, post);
    }
}
