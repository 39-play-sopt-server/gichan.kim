package org.sopt.core.support.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.sopt.core.error.ErrorCode;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
        boolean success,
        String code,
        String message,
        @JsonInclude(JsonInclude.Include.ALWAYS)
        Object data,
        LocalDateTime timestamp,
        String path,
        List<FieldError> errors
) implements BaseResponse {
    public record FieldError(String field, Object rejectedValue, String reason) {
    }

    public static ErrorResponse of(ErrorCode code, String path) {
        return new ErrorResponse(false, code.getCode(), code.getMessage(), null, LocalDateTime.now(), path, null);
    }

    public static ErrorResponse of(ErrorCode code, String path, List<FieldError> errors) {
        return new ErrorResponse(false, code.getCode(), code.getMessage(), null, LocalDateTime.now(), path, errors);
    }
}
