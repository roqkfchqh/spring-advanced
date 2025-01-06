package org.example.expert.application.service;

import lombok.RequiredArgsConstructor;
import org.example.expert.application.manager.ValidationService;
import org.example.expert.common.exception.base.ErrorCode;
import org.example.expert.domain.user.User;
import org.example.expert.infrastructure.repository.UserRepository;
import org.example.expert.application.dto.request.UserRoleChangeRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserAdminService {

    private final UserRepository userRepository;
    private final ValidationService validationService;

    @Transactional
    public void changeUserRole(long userId, final UserRoleChangeRequestDto dto) {
        validationService.validateUserRole(dto.userRole());
        User user = userRepository.findByIdOrThrow(userId, ErrorCode.USER_NOT_FOUND);
        user.updateRole(dto.userRole());
    }
}
