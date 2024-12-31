package org.example.expert.application.tokenprovider;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.user.Email;
import org.example.expert.domain.user.UserRole;
import org.example.expert.infrastructure.jwt.JwtUtil;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider implements TokenProvider {

    private final JwtUtil jwtUtil;

    @Override
    public String createToken(Long userId, Email email, UserRole role){
        return jwtUtil.createToken(userId, String.valueOf(email), UserRole.of(String.valueOf(role)));
    }
}
