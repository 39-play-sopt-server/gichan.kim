package org.sopt.core.domain.reaction;

import org.sopt.core.error.ErrorCode;
import org.springframework.http.HttpStatus;

public enum ReactionErrorCode implements ErrorCode {
    LIKE_ALREADY_EXISTS(HttpStatus.CONFLICT, "REACTION-E001", "이미 좋아요를 누른 게시글입니다."),
    LIKE_NOT_FOUND(HttpStatus.NOT_FOUND, "REACTION-E002", "좋아요를 누르지 않은 게시글입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ReactionErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    @Override
    public HttpStatus getStatus() {
        return status;
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
