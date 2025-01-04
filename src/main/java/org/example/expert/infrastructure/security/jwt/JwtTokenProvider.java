package org.example.expert.infrastructure.security.jwt;

import lombok.RequiredArgsConstructor;
import org.example.expert.application.security.TokenProvider;
import org.example.expert.domain.user.UserRole;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider implements TokenProvider {

    private final JwtUtil jwtUtil;

    @Override
    public String createToken(Long userId, String email, UserRole role){
        return jwtUtil.createToken(userId, email, role);
    }
}
