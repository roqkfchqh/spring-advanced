package org.example.expert.application.manager;

import lombok.RequiredArgsConstructor;
import org.example.expert.application.service.AuthService;
import org.example.expert.domain.user.User;
import org.example.expert.application.dto.request.SigninRequestDto;
import org.example.expert.application.dto.request.SignupRequestDto;
import org.example.expert.application.security.TokenProvider;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthManager {

    private final AuthService authService;
    private final TokenProvider tokenProvider;
    private final ValidationService validationService;

    //signup 전 토큰 발급
    public String handleSignup(SignupRequestDto dto) {
        validationService.validateSignup(dto.email(), dto.password(), dto.userRole());
        User user = authService.signup(dto);
        return tokenProvider.createToken(user.getId(), user.getEmail(), user.getUserRole());
    }

    //signin 전 토큰 발급
    public String handleSignin(SigninRequestDto dto) {
        validationService.validateSignin(dto.email(), dto.password());
        User user = authService.signin(dto);
        return tokenProvider.createToken(user.getId(), user.getEmail(), user.getUserRole());
    }
}