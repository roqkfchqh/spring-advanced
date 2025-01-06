package org.example.expert.infrastructure.security.adminlogging;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.expert.common.exception.business.AuthException;
import org.example.expert.common.exception.base.ErrorCode;
import org.example.expert.common.exception.business.ForbiddenException;
import org.example.expert.common.exception.system.ServerException;
import org.example.expert.infrastructure.security.jwt.JwtUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class AdminAccessInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        String token = request.getHeader("Authorization");

        if (token == null || token.isEmpty()) {
            throw new AuthException(ErrorCode.INVALID_TOKEN);
        }

        try {
            token = jwtUtil.substringToken(token);
            Claims claims = jwtUtil.extractClaims(token);

            String userRole = claims.get("userRole", String.class);
            if (!"ADMIN".equals(userRole)) {
                throw new ForbiddenException(ErrorCode.FORBIDDEN_OPERATION);
            }

            request.setAttribute("startTime", System.currentTimeMillis());
            request.setAttribute("userId", claims.getSubject());
            return true;

        } catch (Exception e) {
            throw new ServerException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}