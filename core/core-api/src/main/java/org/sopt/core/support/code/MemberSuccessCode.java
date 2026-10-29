package org.sopt.core.support.code;

public enum MemberSuccessCode implements SuccessCode {

    MEMBER_CREATED("MEMBER-S001", "회원이 생성되었습니다."),
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
