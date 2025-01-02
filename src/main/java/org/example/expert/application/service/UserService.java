package org.example.expert.application.service;

import lombok.RequiredArgsConstructor;
import org.example.expert.application.helper.EntityFinder;
import org.example.expert.common.exception.ErrorCode;
import org.example.expert.domain.user.User;
import org.example.expert.presentation.external.dto.request.UserChangePasswordRequestDto;
import org.example.expert.domain.user.UserRepository;
import org.example.expert.infrastructure.encoder.PasswordEncoder;
import org.example.expert.common.exception.InvalidRequestException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User getUser(long userId) {
        return EntityFinder.findByIdOrThrow(userRepository, userId, ErrorCode.USER_NOT_FOUND);
    }

    public void changePassword(long userId, final UserChangePasswordRequestDto dto) {

        User user = EntityFinder.findByIdOrThrow(userRepository, userId, ErrorCode.USER_NOT_FOUND);

        if (!passwordEncoder.matches(dto.oldPassword(), user.getPassword())) {
            throw new InvalidRequestException(ErrorCode.WRONG_PASSWORD);
        }

        user.changePassword(passwordEncoder.encode(dto.newPassword()));
        userRepository.save(user);
    }
}
