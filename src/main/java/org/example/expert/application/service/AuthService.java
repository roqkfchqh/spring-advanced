package org.example.expert.application.service;

import lombok.RequiredArgsConstructor;
import org.example.expert.infrastructure.repository.UserRepository;
import org.example.expert.common.exception.base.ErrorCode;
import org.example.expert.domain.user.*;
import org.example.expert.infrastructure.security.encoder.PasswordEncoder;
import org.example.expert.common.exception.business.AuthException;
import org.example.expert.common.exception.business.InvalidRequestException;
import org.example.expert.application.dto.request.SigninRequestDto;
import org.example.expert.application.dto.request.SignupRequestDto;
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

        User newUser = User.of(dto.email(), dto.password(), dto.userRole());

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
