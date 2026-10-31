package org.sopt.core.domain.post;

import org.sopt.core.error.CoreException;
import org.springframework.stereotype.Component;

@Component
public class PostRemover {

    private final PostRepository postRepository;

    public PostRemover(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public void remove(Long id) {
        if (!postRepository.existsById(id)) {
            throw new CoreException(PostErrorCode.POST_NOT_FOUND);
        }
        postRepository.deleteById(id);
    }

    public void removeAllByMemberId(Long memberId) {
        postRepository.deleteAllByMemberId(memberId);
    }
}
