package org.sopt.core.domain.post;

import org.sopt.core.error.CoreException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PostUpdater {

    private final PostRepository postRepository;

    public PostUpdater(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post update(Long id, String title, String content, PostCategory category) {
        return postRepository.update(id, title, content, category)
                .orElseThrow(() -> new CoreException(PostErrorCode.POST_NOT_FOUND));
    }

    public Post increaseViewCount(Long id) {
        return postRepository.increaseViewCount(id)
                .orElseThrow(() -> new CoreException(PostErrorCode.POST_NOT_FOUND));
    }

    public void increaseLikeCount(Long id) {
        if (!postRepository.increaseLikeCount(id)) {
            throw new CoreException(PostErrorCode.POST_NOT_FOUND);
        }
    }

    public void decreaseLikeCount(Long id) {
        if (!postRepository.decreaseLikeCount(id)) {
            throw new CoreException(PostErrorCode.POST_NOT_FOUND);
        }
    }

    public void decreaseLikeCountAll(List<Long> ids) {
        if (ids.isEmpty()) {
            return;
        }
        postRepository.decreaseLikeCountAll(ids);
    }
}
