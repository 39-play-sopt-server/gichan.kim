package org.sopt.core.controller.request;

import org.sopt.core.domain.post.PostCategory;

public record UpdatePostRequest(String title, String content, PostCategory category) {
}
