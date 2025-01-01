package org.example.expert.infrastructure.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.example.expert.infrastructure.jwt.JwtUtil;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class AdminApiLoggingAspect {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final JwtUtil jwtUtil;
    private final HttpServletRequest request;

    @Around("execution(* org.example.expert.interfaces.rest.CommentAdminController.deleteComment(..)) || " +
            "execution(* org.example.expert.interfaces.rest.UserAdminController.changeUserRole(..))")
    public Object logAdminApi(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        String token = request.getHeader("Authorization");
        if (token != null && !token.isEmpty()) {
            token = jwtUtil.substringToken(token);
        }

        Claims claims = jwtUtil.extractClaims(token);
        String userId = claims.getSubject();
        String userRole = claims.get("userRole", String.class);

        Object[] args = joinPoint.getArgs();
        String requestBody = objectMapper.writeValueAsString(args);
        log.info("API 요청 시작: {}, 사용자 ID: {}, 역할: {}, 요청 본문: {}", joinPoint.getSignature(), userId, userRole, requestBody);

        Object response = joinPoint.proceed();

        String responseBody = objectMapper.writeValueAsString(response);
        long duration = System.currentTimeMillis() - startTime;
        log.info("API 요청 종료: {}, 응답 본문: {}, 소요 시간: {}ms", joinPoint.getSignature(), responseBody, duration);

        return response;
    }
}
