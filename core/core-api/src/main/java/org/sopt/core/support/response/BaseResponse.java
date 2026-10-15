package org.sopt.core.support.response;

public sealed interface BaseResponse permits SuccessResponse, ErrorResponse {

    boolean success();

    String code();

    String message();
}
