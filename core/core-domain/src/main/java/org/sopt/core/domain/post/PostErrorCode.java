package org.sopt.core.domain.post;

import org.sopt.core.error.ErrorCode;

public enum PostErrorCode implements ErrorCode {
    POST_NOT_FOUND("POST-E001", "존재하지 않는 게시글입니다."),
    POST_TITLE_EMPTY("POST-E002", "제목은 비어 있을 수 없습니다."),
    POST_CONTENT_EMPTY("POST-E003", "본문은 비어 있을 수 없습니다.");

    private final String code;
    private final String message;

    PostErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
