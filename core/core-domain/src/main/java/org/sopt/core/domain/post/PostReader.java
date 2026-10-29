package org.sopt.core.domain.post;

import org.sopt.core.error.CoreException;
import org.sopt.core.support.PageResult;
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

    public void validateExists(Long id) {
        if (!postRepository.existsById(id)) {
            throw new CoreException(PostErrorCode.POST_NOT_FOUND);
        }
    }

    public List<Long> readIdsByMemberId(Long memberId) {
        return postRepository.findIdsByMemberId(memberId);
    }

    public PageResult<Post> readAll(PostCategory category, String keyword, int page, int size) {
        return postRepository.findAll(category, keyword, page, size);
    }
}
