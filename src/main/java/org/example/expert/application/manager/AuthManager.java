package org.example.expert.application.manager;

import lombok.RequiredArgsConstructor;
import org.example.expert.application.service.AuthService;
import org.example.expert.domain.user.User;
import org.example.expert.presentation.external.dto.request.SigninRequestDto;
import org.example.expert.presentation.external.dto.request.SignupRequestDto;
import org.example.expert.application.tokenprovider.TokenProvider;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthManager {

    private final AuthService authService;
    private final TokenProvider tokenProvider;

    public String handleSignup(SignupRequestDto vo) {
        User user = authService.signup(vo);
        return tokenProvider.createToken(user.getId(), user.getEmail(), user.getUserRole());
    }

    public String handleSignin(SigninRequestDto vo) {
        User user = authService.signin(vo);
        return tokenProvider.createToken(user.getId(), user.getEmail(), user.getUserRole());
    }
}