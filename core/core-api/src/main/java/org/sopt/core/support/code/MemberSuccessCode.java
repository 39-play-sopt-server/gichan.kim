package org.sopt.core.support.code;

public enum MemberSuccessCode implements SuccessCode {

    MEMBER_DELETED("MEMBER-S002", "회원이 삭제되었습니다.");

    private final String code;
    private final String message;

    MemberSuccessCode(String code, String message) {
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
