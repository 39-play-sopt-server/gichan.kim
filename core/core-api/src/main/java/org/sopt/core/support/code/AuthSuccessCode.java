package org.sopt.core.support.code;

public enum AuthSuccessCode implements SuccessCode {

    SIGNED_UP("AUTH-S001", "회원가입이 완료되었습니다."),
    LOGGED_IN("AUTH-S002", "로그인되었습니다."),
    TOKEN_REISSUED("AUTH-S003", "토큰이 재발급되었습니다."),
    LOGGED_OUT("AUTH-S004", "로그아웃되었습니다.");

    private final String code;
    private final String message;

    AuthSuccessCode(String code, String message) {
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
