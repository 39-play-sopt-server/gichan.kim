package org.sopt.core.support.auth;

import org.springframework.stereotype.Component;

@Component
public class FixedMemberIdProvider {

    public Long memberId() {
        return 1L;
    }
}
