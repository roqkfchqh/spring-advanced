package org.example.expert.application.service;

import lombok.RequiredArgsConstructor;
import org.example.expert.common.exception.ErrorCode;
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
            throw new InvalidRequestException(ErrorCode.ALREADY_USED_EMAIL);
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
                () -> new InvalidRequestException(ErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(dto.password(), user.getPassword())) {
            throw new AuthException(ErrorCode.WRONG_EMAIL_OR_PASSWORD);
        }

        return user;
    }
}
