package org.sopt.core.domain.post;

import org.sopt.core.error.CoreException;
import org.springframework.stereotype.Component;

@Component
public class PostValidator {

    public void validate(String title, String content) {
        if (title == null || title.isBlank()) {
            throw new CoreException(PostErrorCode.POST_TITLE_EMPTY);
        }
        if (content == null || content.isBlank()) {
            throw new CoreException(PostErrorCode.POST_CONTENT_EMPTY);
        }
    }

    public void validateForUpdate(String title, String content, PostCategory category) {
        if (title == null && content == null && category == null) {
            throw new CoreException(PostErrorCode.POST_UPDATE_EMPTY);
        }
        if (title != null && title.isBlank()) {
            throw new CoreException(PostErrorCode.POST_TITLE_EMPTY);
        }
        if (content != null && content.isBlank()) {
            throw new CoreException(PostErrorCode.POST_CONTENT_EMPTY);
        }
    }

}
