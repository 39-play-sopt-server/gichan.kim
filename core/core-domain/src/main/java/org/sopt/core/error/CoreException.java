package org.sopt.core.error;

public class CoreException extends RuntimeException{
    private final ErrorCode errorCode;
    private final Object data;

    public CoreException(ErrorCode errorCode) {
        this(errorCode, null);
    }

    public CoreException(ErrorCode errorCode, Object data) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.data = data;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public Object getData() {
        return data;
    }
}
