package org.sopt.core.domain.post;

import org.sopt.core.error.ErrorCode;
import org.springframework.http.HttpStatus;

public enum PostErrorCode implements ErrorCode {
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "POST-E001", "존재하지 않는 게시글입니다."),
    POST_TITLE_EMPTY(HttpStatus.BAD_REQUEST, "POST-E002", "제목은 비어 있을 수 없습니다."),
    POST_CONTENT_EMPTY(HttpStatus.BAD_REQUEST, "POST-E003", "본문은 비어 있을 수 없습니다."),
    POST_UPDATE_EMPTY(HttpStatus.BAD_REQUEST, "POST-E004", "수정할 항목이 하나 이상 필요합니다.");;

    private final HttpStatus status;
    private final String code;
    private final String message;

    PostErrorCode(HttpStatus status, String code, String message) {
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
