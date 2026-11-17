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
    private final TokenIssuer tokenIssuer;
    private final TokenParser tokenParser;

    public AuthService(MemberReader memberReader, MemberAppender memberAppender, PasswordEncoder passwordEncoder, TokenIssuer tokenIssuer, TokenParser tokenParser) {
        this.memberReader = memberReader;
        this.memberAppender = memberAppender;
        this.passwordEncoder = passwordEncoder;
        this.tokenIssuer = tokenIssuer;
        this.tokenParser = tokenParser;
    }

    @Transactional
    public Long signup(String name, String email, String password) {
        memberReader.validateEmailNotExists(email);
        Member member = memberAppender.append(new NewMember(name, email, passwordEncoder.encode(password)));
        return member.getId();
    }

    public TokenPair login(String email, String password) {
        Member member = memberReader.findByEmail(email)
                .filter(found -> passwordEncoder.matches(password, found.getPassword()))
                .orElseThrow(() -> new CoreException(AuthErrorCode.INVALID_CREDENTIALS));
        return new TokenPair(
                tokenIssuer.issue(member.getId(), TokenType.ACCESS),
                tokenIssuer.issue(member.getId(), TokenType.REFRESH)
        );
    }

    public String reissue(String refreshToken) {
        Long memberId = tokenParser.parseMemberId(refreshToken, TokenType.REFRESH);
        memberReader.validateExists(memberId);
        return tokenIssuer.issue(memberId, TokenType.ACCESS);
    }
}
