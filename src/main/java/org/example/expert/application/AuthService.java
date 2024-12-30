package org.example.expert.application;

import lombok.RequiredArgsConstructor;
import org.example.expert.application.tokenprovider.TokenGenerator;
import org.example.expert.application.tokenprovider.TokenSaver;
import org.example.expert.domain.user.*;
import org.example.expert.domain.user.auth.*;
import org.example.expert.infrastructure.PasswordEncoder;
import org.example.expert.infrastructure.exception.AuthException;
import org.example.expert.infrastructure.exception.InvalidRequestException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final TokenGenerator tokenGenerator;
    private final TokenSaver tokenSaver;
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

        //TODO : 레벨6 생각 (왜 두 가지 동작이 ?)
        String token = tokenGenerator.generate(savedUser.getId(), savedUser.getEmail(), userRole);
        tokenSaver.save(token, savedUser.getId());

        return new SignupResponse(token);
    }

    public SigninResponse signin(SigninRequest signinRequest) {
        User user = userRepository.findByEmail(signinRequest.getEmail()).orElseThrow(
                () -> new InvalidRequestException("이메일 또는 비밀번호가 잘못되었습니다."));

        if (!passwordEncoder.matches(signinRequest.getPassword(), user.getPassword())) {
            throw new AuthException("이메일 또는 비밀번호가 잘못되었습니다.");
        }

        String token = tokenGenerator.generate(user.getId(), user.getEmail(), user.getUserRole());
        tokenSaver.save(token, user.getId());

        return new SigninResponse(token);
    }
}
