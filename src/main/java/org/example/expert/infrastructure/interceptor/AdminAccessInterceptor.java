package org.example.expert.infrastructure.interceptor;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
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
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("토큰이 존재하지 않습니다.");
            return false;
        }

        try {
            token = jwtUtil.substringToken(token);
            Claims claims = jwtUtil.extractClaims(token);

            String userRole = claims.get("userRole", String.class);
            if (!"ADMIN".equals(userRole)) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("ADMIN 권한이 없습니다.");
                return false;
            }

            request.setAttribute("startTime", System.currentTimeMillis());
            request.setAttribute("userId", claims.getSubject());
            return true;

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("토큰이 존재하지 않습니다.");
            return false;
        }
    }
}