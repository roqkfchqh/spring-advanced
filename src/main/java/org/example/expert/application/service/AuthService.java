package org.example.expert.application.service;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.user.*;
import org.example.expert.infrastructure.encoder.PasswordEncoder;
import org.example.expert.infrastructure.exception.AuthException;
import org.example.expert.infrastructure.exception.InvalidRequestException;
import org.example.expert.interfaces.external.dto.request.SigninDto;
import org.example.expert.interfaces.external.dto.request.SignupDto;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User signup(final SignupDto dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new InvalidRequestException("이미 존재하는 이메일입니다.");
        }

        User newUser = new User(
                dto.getEmail(),
                passwordEncoder.encode(dto.getPassword()),
                dto.getUserRole()
        );

        return userRepository.save(newUser);
    }

    public User signin(final SigninDto dto) {
        User user = userRepository.findByEmail(dto.getEmail()).orElseThrow(
                () -> new InvalidRequestException("이메일 또는 비밀번호가 잘못되었습니다."));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new AuthException("이메일 또는 비밀번호가 잘못되었습니다.");
        }

        return user;
    }
}
