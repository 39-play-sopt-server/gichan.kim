package org.sopt.core.domain.auth;

public interface TokenIssuer {

    String issue(Long memberId, TokenType tokenType);
}
