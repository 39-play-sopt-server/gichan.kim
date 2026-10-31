package org.sopt.core.controller.request;

import jakarta.validation.constraints.AssertTrue;
import org.sopt.core.domain.post.PostCategory;

public record UpdatePostRequest(String title, String content, PostCategory category) {

    @AssertTrue(message = "수정할 항목이 하나 이상 필요합니다.")
    public boolean isUpdatable() {
        return title != null || content != null || category != null;
    }
}
