package org.sopt.core.support.response;

import org.sopt.core.error.ErrorCode;

public class ApiResponse<T> {

    private final boolean success;
    private final String code;
    private final String message;
    private final T data;

    private ApiResponse(boolean success, String code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> success(SuccessCode successCode, T data) {
        return new ApiResponse<>(true, successCode.getCode(), successCode.getMessage(), data);
    }

    public static ApiResponse<Void> success(SuccessCode successCode) {
        return new ApiResponse<>(true, successCode.getCode(), successCode.getMessage(), null);
    }

    public static <T> ApiResponse<T> error(ErrorCode errorCode) {
        return new ApiResponse<>(false, errorCode.getCode(), errorCode.getMessage(), null);
    }

    public boolean isSuccess() { return success; }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
