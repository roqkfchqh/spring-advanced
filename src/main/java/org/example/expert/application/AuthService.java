package org.example.expert.application;

import lombok.RequiredArgsConstructor;
import org.example.expert.application.tokenprovider.TokenProvider;
import org.example.expert.domain.user.*;
import org.example.expert.infrastructure.PasswordEncoder;
import org.example.expert.infrastructure.exception.AuthException;
import org.example.expert.infrastructure.exception.InvalidRequestException;
import org.example.expert.interfaces.dto.user.SigninRequest;
import org.example.expert.interfaces.dto.user.SigninResponse;
import org.example.expert.interfaces.dto.user.SignupRequest;
import org.example.expert.interfaces.dto.user.SignupResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;

    public SignupResponse signup(SignupRequest signupRequest) {
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new InvalidRequestException("이미 존재하는 이메일입니다.");
        }

        UserRole userRole = UserRole.of(signupRequest.getUserRole());

        User newUser = new User(
                signupRequest.getEmail(),
                passwordEncoder.encode(signupRequest.getPassword()),
                userRole
        );
        User savedUser = userRepository.save(newUser);

        String bearerToken = tokenProvider.createToken(savedUser.getId(), savedUser.getEmail(), userRole);

        return new SignupResponse(bearerToken);
    }

    public SigninResponse signin(SigninRequest signinRequest) {
        User user = userRepository.findByEmail(signinRequest.getEmail()).orElseThrow(
                () -> new InvalidRequestException("이메일 또는 비밀번호가 잘못되었습니다."));

        if (!passwordEncoder.matches(signinRequest.getPassword(), user.getPassword())) {
            throw new AuthException("이메일 또는 비밀번호가 잘못되었습니다.");
        }

        String bearerToken = tokenProvider.createToken(user.getId(), user.getEmail(), user.getUserRole());

        return new SigninResponse(bearerToken);
    }
}
