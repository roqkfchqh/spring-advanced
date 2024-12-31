package org.example.expert.application;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.user.User;
import org.example.expert.domain.user.UserRepository;
import org.example.expert.domain.user.UserRole;
import org.example.expert.interfaces.dto.user.UserRoleChangeRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAdminService {

    private final UserRepository userRepository;

    public void changeUserRole(long userId, UserRoleChangeRequest userRoleChangeRequest) {
        User user = EntityFinder.findByIdOrThrow(userRepository, userId, "User not found");
        user.updateRole(UserRole.of(userRoleChangeRequest.getRole()));
    }
}
