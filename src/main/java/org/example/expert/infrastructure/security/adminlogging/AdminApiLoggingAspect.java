package org.example.expert.infrastructure.security.adminlogging;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.example.expert.common.exception.base.ErrorCode;
import org.example.expert.common.exception.business.ForbiddenException;
import org.example.expert.common.exception.system.ServerException;
import org.example.expert.infrastructure.security.jwt.JwtUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class AdminApiLoggingAspect {

    private final JwtUtil jwtUtil;
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final ObjectMapper objectMapper;

    @Around("execution(* org.example.expert.presentation.rest.CommentAdminController.deleteComment(..)) || " +
            "execution(* org.example.expert.presentation.rest.UserAdminController.changeUserRole(..))")
    public Object logAdminApi(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        //래핑된 객체 사용전 확인
        if (!(request instanceof ContentCachingRequestWrapper wrappedRequest) || !(response instanceof ContentCachingResponseWrapper wrappedResponse)) {
            throw new ServerException(ErrorCode.CACHING_WRAPPER_ERROR);
        }

        //token 에서 사용자 정보와 역할 추출
        String token = request.getHeader("Authorization");
        if (token != null && !token.isEmpty()) {
            token = jwtUtil.substringToken(token);
        }

        Claims claims = jwtUtil.extractClaims(token);
        String userId = claims.getSubject();
        String userRole = claims.get("userRole", String.class);

        if (!"ADMIN".equals(userRole)) {
            log.warn("권한이 없는 사용자가 접근을 시도했습니다. 사용자 ID: {}, 역할: {}", userId, userRole);
            throw new ForbiddenException(ErrorCode.FORBIDDEN_OPERATION);
        }

        //요청
        String requestBodyJson = null;
        try {
            requestBodyJson = objectMapper.writeValueAsString(objectMapper.readTree(wrappedRequest.getContentAsByteArray()));
        } catch (Exception e) {
            log.error("요청 본문 JSON 변환 중 에러 발생: {}", e.getMessage());
        }
        String requestUrl = request.getRequestURI();
        log.info("API 요청 시작: URL: {}, 메서드: {}, 사용자 ID: {}, 역할: {}, 요청 본문: {}", requestUrl, joinPoint.getSignature(), userId, userRole, requestBodyJson);

        try {
            Object result = joinPoint.proceed();

            //응답
            String responseBodyJson = null;
            try {
                responseBodyJson = objectMapper.writeValueAsString(objectMapper.readTree(wrappedResponse.getContentAsByteArray()));
            } catch (Exception e) {
                log.error("응답 본문 JSON 변환 중 에러 발생: {}", e.getMessage());
            }
            long duration = System.currentTimeMillis() - startTime;
            log.info("API 요청 종료: URL: {}, 응답 본문: {}, 소요 시간: {}ms", requestUrl, responseBodyJson, duration);

            return result;
        } catch (Exception e) {
            log.error("API 요청 중 에러 발생: URL: {}, 사용자 ID: {}, 에러 메시지: {}", requestUrl, userId, e.getMessage());
            throw e;
        } finally {
            wrappedResponse.copyBodyToResponse();
        }
    }
}