package org.example.expert.application;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.user.User;
import org.example.expert.domain.user.UserChangePasswordRequest;
import org.example.expert.domain.user.UserRepository;
import org.example.expert.domain.user.UserResponse;
import org.example.expert.infrastructure.PasswordEncoder;
import org.example.expert.infrastructure.exception.InvalidRequestException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse getUser(long userId) {
        User user = EntityFinder.findByIdOrThrow(userRepository, userId, "User not found");
        return new UserResponse(user.getId(), user.getEmail());
    }

    public void changePassword(long userId, UserChangePasswordRequest userChangePasswordRequest) {
        if (userChangePasswordRequest.getNewPassword().length() < 8 ||
                !userChangePasswordRequest.getNewPassword().matches(".*\\d.*") ||
                !userChangePasswordRequest.getNewPassword().matches(".*[A-Z].*")) {
            throw new InvalidRequestException("새 비밀번호는 8자 이상이어야 하고, 숫자와 대문자를 포함해야 합니다.");
        }

        User user = EntityFinder.findByIdOrThrow(userRepository, userId, "User not found");

        if (passwordEncoder.matches(userChangePasswordRequest.getNewPassword(), user.getPassword())) {
            throw new InvalidRequestException("새 비밀번호는 기존 비밀번호와 같을 수 없습니다.");
        }

        if (!passwordEncoder.matches(userChangePasswordRequest.getOldPassword(), user.getPassword())) {
            throw new InvalidRequestException("잘못된 비밀번호입니다.");
        }

        user.changePassword(passwordEncoder.encode(userChangePasswordRequest.getNewPassword()));
    }
}
