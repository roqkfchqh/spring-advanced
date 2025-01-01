package org.example.expert.application.service;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.user.*;
import org.example.expert.infrastructure.encoder.PasswordEncoder;
import org.example.expert.infrastructure.exception.AuthException;
import org.example.expert.infrastructure.exception.InvalidRequestException;
import org.example.expert.interfaces.external.dto.request.SigninVo;
import org.example.expert.interfaces.external.dto.request.SignupVo;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User signup(final SignupVo vo) {
        if (userRepository.existsByEmail(vo.getEmail())) {
            throw new InvalidRequestException("이미 존재하는 이메일입니다.");
        }

        User newUser = new User(
                vo.getEmail(),
                passwordEncoder.encode(vo.getPassword()),
                vo.getUserRole()
        );

        return userRepository.save(newUser);
    }

    public User signin(final SigninVo vo) {
        User user = userRepository.findByEmail(vo.getEmail()).orElseThrow(
                () -> new InvalidRequestException("이메일 또는 비밀번호가 잘못되었습니다."));

        if (!passwordEncoder.matches(vo.getPassword(), user.getPassword())) {
            throw new AuthException("이메일 또는 비밀번호가 잘못되었습니다.");
        }

        return user;
    }
}
