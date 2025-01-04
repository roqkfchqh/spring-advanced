package org.example.expert.application.mapper;

import org.example.expert.domain.user.User;
import org.example.expert.application.dto.response.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements Mapper<User, UserResponse> {

    @Override
    public UserResponse toDto(User entity) {
        return new UserResponse(
                entity.getId(),
                entity.getEmail()
        );
    }
}
