package org.example.expert.application.service;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.user.*;
import org.example.expert.infrastructure.encoder.PasswordEncoder;
import org.example.expert.common.exception.AuthException;
import org.example.expert.common.exception.InvalidRequestException;
import org.example.expert.presentation.external.dto.request.SigninRequestDto;
import org.example.expert.presentation.external.dto.request.SignupRequestDto;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User signup(final SignupRequestDto dto) {
        if (userRepository.existsByEmail(dto.email())) {
            throw new InvalidRequestException("이미 존재하는 이메일입니다.");
        }

        User newUser = new User(
                dto.email(),
                passwordEncoder.encode(dto.password()),
                dto.userRole()
        );

        return userRepository.save(newUser);
    }

    public User signin(final SigninRequestDto dto) {
        User user = userRepository.findByEmail(dto.email()).orElseThrow(
                () -> new InvalidRequestException("이메일 또는 비밀번호가 잘못되었습니다."));

        if (!passwordEncoder.matches(dto.password(), user.getPassword())) {
            throw new AuthException("이메일 또는 비밀번호가 잘못되었습니다.");
        }

        return user;
    }
}
