package org.example.expert.interfaces.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.todo.Todo;
import org.example.expert.domain.user.auth.Auth;
import org.example.expert.domain.user.auth.AuthUser;
import org.example.expert.interfaces.external.dto.request.TodoSaveRequest;
import org.example.expert.interfaces.external.dto.response.TodoResponse;
import org.example.expert.application.service.TodoService;
import org.example.expert.interfaces.external.mapper.TodoMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;
    private final TodoMapper todoMapper;

    @PostMapping("/todos")
    public ResponseEntity<TodoResponse> saveTodo(
            @Auth AuthUser authUser,
            @Valid @RequestBody TodoSaveRequest todoSaveRequest
    ) {
        Todo todo = todoService.saveTodo(authUser, todoSaveRequest);
        return ResponseEntity.ok(todoMapper.toDto(todo));
    }

    @GetMapping("/todos")
    public ResponseEntity<Page<TodoResponse>> getTodos(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<Todo> todo = todoService.getTodos(page, size);
        return ResponseEntity.ok(todo
                .map(todoMapper::toDto));
    }

    @GetMapping("/todos/{todoId}")
    public ResponseEntity<TodoResponse> getTodo(
            @PathVariable long todoId
    ) {
        Todo todo = todoService.getTodo(todoId);
        return ResponseEntity.ok(todoMapper.toDto(todo));
    }
}
