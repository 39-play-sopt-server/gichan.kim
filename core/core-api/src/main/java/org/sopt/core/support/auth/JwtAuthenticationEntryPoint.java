package org.sopt.core.support.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.sopt.core.error.CoreException;
import org.sopt.core.error.ErrorCode;
import org.sopt.core.support.code.CommonErrorCode;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final HandlerExceptionResolver handlerExceptionResolver;

    public JwtAuthenticationEntryPoint(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver handlerExceptionResolver) {
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) {
        handlerExceptionResolver.resolveException(request, response, null, new CoreException(resolveErrorCode(request)));
    }

    private ErrorCode resolveErrorCode(HttpServletRequest request) {
        if (request.getAttribute(JwtAuthenticationFilter.AUTH_ERROR_ATTRIBUTE) instanceof ErrorCode errorCode) {
            return errorCode;
        }
        return CommonErrorCode.UNAUTHORIZED;
    }
}
