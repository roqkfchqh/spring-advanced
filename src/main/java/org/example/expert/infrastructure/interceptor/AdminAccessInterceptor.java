package org.example.expert.infrastructure.interceptor;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.expert.infrastructure.exception.AuthException;
import org.example.expert.infrastructure.exception.ForbiddenException;
import org.example.expert.infrastructure.jwt.JwtUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class AdminAccessInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");

        if (token == null || token.isEmpty()) {
            throw new AuthException("토큰이 존재하지 않습니다.");
        }

        try {
            token = jwtUtil.substringToken(token);
            Claims claims = jwtUtil.extractClaims(token);

            String userRole = claims.get("userRole", String.class);
            if (!"ADMIN".equals(userRole)) {
                throw new ForbiddenException("ADMIN 권한이 없습니다.");
            }

            request.setAttribute("startTime", System.currentTimeMillis());
            request.setAttribute("userId", claims.getSubject());
            return true;

        } catch (Exception e) {
            throw new AuthException("토큰이 존재하지 않습니다.");
        }
    }
}