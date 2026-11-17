package org.sopt.core.domain.auth;

import org.sopt.core.domain.member.Member;
import org.sopt.core.domain.member.MemberAppender;
import org.sopt.core.domain.member.MemberReader;
import org.sopt.core.domain.member.NewMember;
import org.sopt.core.error.CoreException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final MemberReader memberReader;
    private final MemberAppender memberAppender;
    private final PasswordEncoder passwordEncoder;

    public AuthService(MemberReader memberReader, MemberAppender memberAppender, PasswordEncoder passwordEncoder) {
        this.memberReader = memberReader;
        this.memberAppender = memberAppender;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Long signup(String name, String email, String password) {
        memberReader.validateEmailNotExists(email);
        Member member = memberAppender.append(new NewMember(name, email, passwordEncoder.encode(password)));
        return member.getId();
    }

    public Long login(String email, String password) {
        Member member = memberReader.findByEmail(email)
                .filter(found -> passwordEncoder.matches(password, found.getPassword()))
                .orElseThrow(() -> new CoreException(AuthErrorCode.INVALID_CREDENTIALS));
        return member.getId();
    }
}
