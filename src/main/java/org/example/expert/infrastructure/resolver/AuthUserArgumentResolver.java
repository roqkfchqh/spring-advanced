package org.example.expert.infrastructure.resolver;

import jakarta.servlet.http.HttpServletRequest;
import org.example.expert.common.exception.AuthException;
import org.example.expert.common.exception.ErrorCode;
import org.example.expert.presentation.utils.Auth;
import org.example.expert.presentation.utils.AuthUser;
import org.example.expert.domain.user.UserRole;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class AuthUserArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasAuthAnnotation = parameter.getParameterAnnotation(Auth.class) != null;
        boolean isAuthUserType = parameter.getParameterType().equals(AuthUser.class);

        if (hasAuthAnnotation != isAuthUserType) {
            throw new AuthException(ErrorCode.AUTH);
        }

        return hasAuthAnnotation;
    }

    @Override
    public Object resolveArgument(
            @Nullable MethodParameter parameter,
            @Nullable ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            @Nullable WebDataBinderFactory binderFactory
    ) {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

        Long userId = (Long) request.getAttribute("userId");
        String email = (String) request.getAttribute("email");
        UserRole userRole = (UserRole) request.getAttribute("userRole");

        return new AuthUser(userId, email, userRole);
    }
}

/*TODO
전역 예외처리 추가, 테스트코드 작성
 */
