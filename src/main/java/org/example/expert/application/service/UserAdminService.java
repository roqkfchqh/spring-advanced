package org.example.expert.application.service;

import lombok.RequiredArgsConstructor;
import org.example.expert.application.helper.EntityFinder;
import org.example.expert.domain.user.User;
import org.example.expert.domain.user.UserRepository;
import org.example.expert.interfaces.external.dto.request.UserRoleChangeDto;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAdminService {

    private final UserRepository userRepository;

    public void changeUserRole(long userId, final UserRoleChangeDto dto) {
        User user = EntityFinder.findByIdOrThrow(userRepository, userId, "User not found");
        user.updateRole(dto.getRole());
        userRepository.save(user);
    }
}
