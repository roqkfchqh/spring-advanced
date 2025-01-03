package org.example.expert.presentation.external.mapper;

import org.example.expert.domain.user.manager.Manager;
import org.example.expert.presentation.external.dto.response.ManagerResponse;
import org.example.expert.presentation.external.dto.response.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class ManagerMapper implements Mapper<Manager, ManagerResponse> {

    @Override
    public ManagerResponse toDto(Manager entity) {
        return new ManagerResponse(
                entity.getId(),
                new UserResponse(entity.getUser().getId(), entity.getUser().getEmail())
        );
    }
}