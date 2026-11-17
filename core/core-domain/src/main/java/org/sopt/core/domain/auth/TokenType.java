package org.sopt.core.domain.auth;

public enum TokenType {
    ACCESS,
    REFRESH;

    public static final String CLAIM_KEY = "type";
}
