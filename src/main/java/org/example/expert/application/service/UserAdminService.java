package org.example.expert.application.service;

import lombok.RequiredArgsConstructor;
import org.example.expert.application.helper.EntityFinder;
import org.example.expert.domain.user.User;
import org.example.expert.domain.user.UserRepository;
import org.example.expert.domain.user.UserRole;
import org.example.expert.interfaces.external.dto.request.UserRoleChangeRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAdminService {

    private final UserRepository userRepository;

    //TODO : 트랜잭션 달아야함
    public void changeUserRole(long userId, UserRoleChangeRequest userRoleChangeRequest) {
        User user = EntityFinder.findByIdOrThrow(userRepository, userId, "User not found");
        user.updateRole(UserRole.of(userRoleChangeRequest.getRole()));
    }
}