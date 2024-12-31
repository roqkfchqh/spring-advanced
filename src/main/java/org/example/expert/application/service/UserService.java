package org.example.expert.application.service;

import lombok.RequiredArgsConstructor;
import org.example.expert.application.helper.EntityFinder;
import org.example.expert.domain.user.User;
import org.example.expert.interfaces.external.dto.request.UserChangePasswordVo;
import org.example.expert.domain.user.UserRepository;
import org.example.expert.infrastructure.PasswordEncoder;
import org.example.expert.infrastructure.exception.InvalidRequestException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User getUser(long userId) {
        return EntityFinder.findByIdOrThrow(userRepository, userId, "User not found");
    }

    public void changePassword(long userId, UserChangePasswordVo vo) {

        User user = EntityFinder.findByIdOrThrow(userRepository, userId, "User not found");

        if (!passwordEncoder.matches(vo.getOldPassword(), user.getPassword())) {
            throw new InvalidRequestException("잘못된 비밀번호입니다.");
        }

        user.changePassword(passwordEncoder.encode(vo.getNewPassword()));
        userRepository.save(user);
    }
}
