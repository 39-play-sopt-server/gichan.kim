package org.sopt.core.error;

import org.sopt.core.support.BaseCode;
import org.springframework.http.HttpStatus;

public interface ErrorCode extends BaseCode {
    HttpStatus getStatus();
}
