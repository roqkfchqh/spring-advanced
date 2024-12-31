package org.example.expert.application.manager;

import lombok.RequiredArgsConstructor;
import org.example.expert.application.service.AuthService;
import org.example.expert.domain.user.User;
import org.example.expert.interfaces.external.dto.request.SigninRequest;
import org.example.expert.interfaces.external.dto.request.SignupRequest;
import org.example.expert.application.tokenprovider.TokenProvider;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthManager {

    private final AuthService authService;
    private final TokenProvider tokenProvider;

    public String handleSignup(SignupRequest signupRequest) {
        User user = authService.signup(signupRequest);
        return tokenProvider.createToken(user.getId(), user.getEmail(), user.getUserRole());
    }

    public String handleSignin(SigninRequest signinRequest) {
        User user = authService.signin(signinRequest);
        return tokenProvider.createToken(user.getId(), user.getEmail(), user.getUserRole());
    }
}