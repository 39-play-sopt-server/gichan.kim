package org.sopt.core.support.swagger;

import java.lang.annotation.*;

import org.sopt.core.error.ErrorCode;

// Swagger 문서 API 실패 예외 표시
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(ApiExceptions.class)
public @interface ApiException {

    Class<? extends ErrorCode> value();

    String[] codes() default {};
}