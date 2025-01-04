package org.example.expert.application.service;

import lombok.RequiredArgsConstructor;
import org.example.expert.application.dto.response.UserResponse;
import org.example.expert.application.manager.ValidationService;
import org.example.expert.application.mapper.Mapper;
import org.example.expert.common.exception.ErrorCode;
import org.example.expert.domain.user.User;
import org.example.expert.application.dto.request.UserChangePasswordRequestDto;
import org.example.expert.infrastructure.repository.UserRepository;
import org.example.expert.infrastructure.security.encoder.PasswordEncoder;
import org.example.expert.common.exception.InvalidRequestException;
import org.example.expert.presentation.utils.AuthUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ValidationService validationService;
    private final Mapper<User, UserResponse> mapper;

    public UserResponse getUser(long userId) {
        User user = userRepository.findByIdOrThrow(userId, ErrorCode.USER_NOT_FOUND);
        return mapper.toDto(user);
    }

    @Transactional
    public void changePassword(AuthUser authUser, final UserChangePasswordRequestDto dto) {
        validationService.validateChangePassword(dto.oldPassword(), dto.newPassword());
        User user = userRepository.findByIdOrThrow(authUser.id(), ErrorCode.USER_NOT_FOUND);

        if (!passwordEncoder.matches(dto.oldPassword(), user.getPassword())) {
            throw new InvalidRequestException(ErrorCode.WRONG_PASSWORD);
        }

        user.changePassword(passwordEncoder.encode(dto.newPassword()));
    }
}
