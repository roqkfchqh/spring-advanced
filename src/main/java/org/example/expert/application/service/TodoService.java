package org.example.expert.application.service;

import lombok.RequiredArgsConstructor;
import org.example.expert.application.helper.EntityFinder;
import org.example.expert.common.exception.ErrorCode;
import org.example.expert.domain.todo.*;
import org.example.expert.presentation.external.dto.request.TodoSaveRequestDto;
import org.example.expert.presentation.external.weather.WeatherClient;
import org.example.expert.domain.user.auth.AuthUser;
import org.example.expert.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;
    private final WeatherClient weatherClient;

    @Transactional
    public Todo saveTodo(AuthUser authUser, final TodoSaveRequestDto dto) {
        User user = User.fromAuthUser(authUser);

        String weather = weatherClient.getTodayWeather();

        Todo newTodo = new Todo(
                dto.title(),
                dto.contents(),
                weather,
                user
        );

        return todoRepository.save(newTodo);
    }

    public Page<Todo> getTodos(Pageable pageable) {
        return todoRepository.findAllByOrderByModifiedAtDesc(pageable);
    }

    public Todo getTodo(long todoId) {
        return EntityFinder.findByIdOrThrow(todoRepository, todoId, ErrorCode.TODO_NOT_FOUND);
    }
}
