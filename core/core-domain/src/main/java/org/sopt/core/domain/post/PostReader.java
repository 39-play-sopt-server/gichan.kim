package org.sopt.core.domain.post;

import org.sopt.core.error.CoreException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PostReader {

    private final PostRepository postRepository;

    public PostReader(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post read(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new CoreException(PostErrorCode.POST_NOT_FOUND));
    }

    public List<Post> readAll(PostCategory category, int page, int size) {
        return postRepository.findAll(category, page, size);
    }
}
