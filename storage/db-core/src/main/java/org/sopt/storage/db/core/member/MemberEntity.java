package org.sopt.storage.db.core.member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.sopt.core.domain.member.Member;
import org.sopt.core.domain.member.NewMember;
import org.sopt.storage.db.core.BaseEntity;

@Entity
@Table(name = "member")
public class MemberEntity extends BaseEntity {

    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    protected MemberEntity() {}

    private MemberEntity(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public static MemberEntity of(NewMember newMember) {
        return new MemberEntity(newMember.name(), newMember.email(), newMember.password());
    }

    public Member toDomain() {
        return Member.create(getId(), name, email, password, getCreatedAt(), getUpdatedAt());
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
}
