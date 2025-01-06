package org.example.expert.application.service;

import lombok.RequiredArgsConstructor;
import org.example.expert.application.dto.response.TodoResponse;
import org.example.expert.application.mapper.Mapper;
import org.example.expert.domain.todo.TodoRepository;
import org.example.expert.common.exception.base.ErrorCode;
import org.example.expert.domain.todo.*;
import org.example.expert.application.dto.request.TodoSaveRequestDto;
import org.example.expert.infrastructure.external.WeatherClient;
import org.example.expert.presentation.utils.AuthUser;
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
    private final Mapper<Todo, TodoResponse> mapper;

    @Transactional
    public TodoResponse saveTodo(AuthUser authUser, final TodoSaveRequestDto dto) {
        User user = User.fromAuthUser(authUser);
        String weather = weatherClient.getTodayWeather();

        Todo newTodo = Todo.of(dto.title(), dto.contents(), weather, user);
        todoRepository.save(newTodo);
        return mapper.toDto(newTodo);
    }

    public Page<TodoResponse> getTodos(Pageable pageable) {
        Page<Todo> todos= todoRepository.findAllByOrderByModifiedAtDesc(pageable);
        return todos.map(mapper::toDto);
    }

    public TodoResponse getTodo(long todoId) {
        Todo todo = todoRepository.findByIdOrThrow(todoId, ErrorCode.TODO_NOT_FOUND);
        return mapper.toDto(todo);
    }
}
