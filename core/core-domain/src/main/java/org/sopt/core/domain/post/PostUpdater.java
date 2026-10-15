package org.sopt.core.domain.post;

import org.sopt.core.error.CoreException;
import org.springframework.stereotype.Component;

@Component
public class PostUpdater {

    private final PostRepository postRepository;
    private final PostValidator postValidator;

    public PostUpdater(PostRepository postRepository, PostValidator postValidator) {
        this.postRepository = postRepository;
        this.postValidator = postValidator;
    }

    public Post update(Long id, String title, String content, PostCategory category) {
        postValidator.validate(title, content);
        return postRepository.update(id, title, content, category)
                .orElseThrow(() -> new CoreException(PostErrorCode.POST_NOT_FOUND));
    }

    public Post increaseViewCount(Long id) {
        return postRepository.increaseViewCount(id)
                .orElseThrow(() -> new CoreException(PostErrorCode.POST_NOT_FOUND));
    }
}
