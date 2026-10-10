package org.sopt.core.domain.post;

public enum PostCategory {
    FREE("자유 게시판"),
    SECRET("비밀 게시판");

    private final String value;

    PostCategory(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
