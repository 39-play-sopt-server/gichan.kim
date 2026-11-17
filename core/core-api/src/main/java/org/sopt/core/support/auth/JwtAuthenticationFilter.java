package org.sopt.core.support.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.sopt.core.domain.auth.TokenParser;
import org.sopt.core.domain.auth.TokenType;
import org.sopt.core.error.CoreException;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    static final String AUTH_ERROR_ATTRIBUTE = "authErrorCode";
    private static final String BEARER_PREFIX = "Bearer ";

    private final TokenParser tokenParser;

    public JwtAuthenticationFilter(TokenParser tokenParser) {
        this.tokenParser = tokenParser;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = resolveToken(request);

        if (token != null) {
            try {
                Long memberId = tokenParser.parseMemberId(token, TokenType.ACCESS);
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(memberId, null, List.of());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (CoreException e) {
                request.setAttribute(AUTH_ERROR_ATTRIBUTE, e.getErrorCode());
            }
        }

        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null && header.startsWith(BEARER_PREFIX)) {
            return header.substring(BEARER_PREFIX.length());
        }
        return null;
    }
}
