package org.sopt.storage.db.core.member;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.sopt.core.domain.member.Member;
import org.sopt.core.domain.member.NewMember;
import org.sopt.storage.db.core.BaseEntity;

@Entity
@Table(name = "member")
public class MemberEntity extends BaseEntity {

    private String name;
    private String email;

    protected MemberEntity() {}

    private MemberEntity(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public static MemberEntity of(NewMember newMember) {
        return new MemberEntity(newMember.name(), newMember.email());
    }

    public Member toDomain() {
        return Member.create(getId(), name, email, getCreatedAt(), getUpdatedAt());
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
