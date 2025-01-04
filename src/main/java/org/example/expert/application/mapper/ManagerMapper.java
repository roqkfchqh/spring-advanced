package org.example.expert.application.mapper;

import org.example.expert.domain.todo.Manager;
import org.example.expert.application.dto.response.ManagerResponse;
import org.example.expert.application.dto.response.UserResponse;
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