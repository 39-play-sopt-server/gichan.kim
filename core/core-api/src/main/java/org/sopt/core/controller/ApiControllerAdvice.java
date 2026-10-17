package org.sopt.core.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.sopt.core.error.CoreException;
import org.sopt.core.error.ErrorCode;
import org.sopt.core.support.code.CommonErrorCode;
import org.sopt.core.support.response.ErrorResponse;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Set;

@RestControllerAdvice
public class ApiControllerAdvice {

    // CoreException 에러
    @ExceptionHandler(CoreException.class)
    public ResponseEntity<ErrorResponse> handleCoreException(CoreException e, HttpServletRequest request) {
        return error(e.getErrorCode(), request);
    }

    // path variable, query param 타입 변환 실패
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException e, HttpServletRequest request) {
        return error(CommonErrorCode.INVALID_INPUT, request);
    }

    // 깨진 JSON, category에 없는 enum 값 등 요청 바디 파싱 실패
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleNotReadable(HttpMessageNotReadableException e, HttpServletRequest request) {
        return error(CommonErrorCode.INVALID_INPUT, request);
    }

    // 매핑되지 않은 경로
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResourceFound(NoResourceFoundException e, HttpServletRequest request) {
        return error(CommonErrorCode.NOT_FOUND, request);
    }

    // 지원하지 않는 HTTP 메서드
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotSupported(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
        ResponseEntity.BodyBuilder builder = ResponseEntity.status(CommonErrorCode.METHOD_NOT_ALLOWED.getStatus());
        Set<HttpMethod> supported = e.getSupportedHttpMethods();
        if (supported != null && !supported.isEmpty()) {
            builder.allow(supported.toArray(HttpMethod[]::new));
        }
        return builder.body(ErrorResponse.of(CommonErrorCode.METHOD_NOT_ALLOWED, request.getRequestURI()));
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMediaTypeNotSupported(HttpMediaTypeNotSupportedException e, HttpServletRequest request) {
        return error(CommonErrorCode.UNSUPPORTED_MEDIA_TYPE, request);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ErrorResponse> handleHandlerMethodValidation(
            HandlerMethodValidationException e,
            HttpServletRequest request) {
        return ResponseEntity.status(CommonErrorCode.INVALID_INPUT.getStatus())
                .body(ErrorResponse.of(CommonErrorCode.INVALID_INPUT, request.getRequestURI()));
    }

    // 나머지는 500 에러 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e, HttpServletRequest request) {
        return error(CommonErrorCode.INTERNAL, request);
    }

    private ResponseEntity<ErrorResponse> error(ErrorCode code, HttpServletRequest request) {
        return ResponseEntity.status(code.getStatus())
                .body(ErrorResponse.of(code, request.getRequestURI()));
    }
}
