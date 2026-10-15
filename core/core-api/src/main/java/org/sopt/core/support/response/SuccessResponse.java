package org.sopt.core.support.response;

import org.sopt.core.support.code.SuccessCode;

public record SuccessResponse<T>(boolean success, String code, String message, T data)
        implements BaseResponse {

    public static <T> SuccessResponse<T> of(SuccessCode code, T data) {
        return new SuccessResponse<>(true, code.getCode(), code.getMessage(), data);
    }

    public static SuccessResponse<Void> of(SuccessCode code) {
        return new SuccessResponse<>(true, code.getCode(), code.getMessage(), null);
    }
}
