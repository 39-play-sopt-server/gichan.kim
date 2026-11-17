package org.sopt.core.domain.auth;

public interface TokenParser {

    Long parseMemberId(String token, TokenType tokenType);
}
