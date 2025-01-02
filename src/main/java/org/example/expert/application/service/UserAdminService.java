package org.example.expert.application.service;

import lombok.RequiredArgsConstructor;
import org.example.expert.application.helper.EntityFinder;
import org.example.expert.common.exception.ErrorCode;
import org.example.expert.domain.user.User;
import org.example.expert.domain.user.UserRepository;
import org.example.expert.presentation.external.dto.request.UserRoleChangeRequestDto;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAdminService {

    private final UserRepository userRepository;

    public void changeUserRole(long userId, final UserRoleChangeRequestDto dto) {
        User user = EntityFinder.findByIdOrThrow(userRepository, userId, ErrorCode.USER_NOT_FOUND);
        user.updateRole(dto.userRole());
        userRepository.save(user);
    }
}
