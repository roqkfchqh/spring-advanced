package org.example.expert.interfaces.external.mapper;

import org.example.expert.domain.todo.Todo;
import org.example.expert.interfaces.external.dto.response.TodoResponse;
import org.example.expert.interfaces.external.dto.response.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class TodoMapper implements Mapper<Todo, TodoResponse> {

    @Override
    public TodoResponse toDto(Todo entity) {
        return new TodoResponse(
                entity.getId(),
                entity.getTitle(),
                entity.getContents(),
                entity.getWeather(),
                new UserResponse(entity.getUser().getId(), entity.getUser().getEmail()),
                entity.getCreatedAt(),
                entity.getModifiedAt()
        );
    }
}
