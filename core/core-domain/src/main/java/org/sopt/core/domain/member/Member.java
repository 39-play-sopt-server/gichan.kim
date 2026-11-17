package org.sopt.core.domain.member;

import java.time.LocalDateTime;

public class Member {
    private final Long id;
    private final String name;
    private final String email;
    private final String password;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    private Member(Long id, String name, String email, String password, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Member create(Long id, String name, String email, String password, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new Member(id, name, email, password, createdAt, updatedAt);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
