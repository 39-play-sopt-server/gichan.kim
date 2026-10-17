package org.sopt.core.support.swagger;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.sopt.core.support.code.SuccessCode;

// Swagger 문서 API 성공코드 표시
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiSuccess {

    Class<? extends SuccessCode> value();

    String[] codes() default {};
}
