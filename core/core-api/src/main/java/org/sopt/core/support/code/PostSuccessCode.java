package org.sopt.core.support.code;

public enum PostSuccessCode implements SuccessCode {

    POST_CREATED("POST-S001", "게시글이 작성되었습니다."),
    POST_LIST_READ("POST-S002", "게시글 목록을 조회했습니다."),
    POST_READ("POST-S003", "게시글을 조회했습니다."),
    POST_UPDATED("POST-S004", "게시글이 수정되었습니다."),
    POST_DELETED("POST-S005", "게시글이 삭제되었습니다.");

    private final String code;
    private final String message;

    PostSuccessCode(String code, String message) {
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