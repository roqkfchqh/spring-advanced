package org.example.expert.presentation.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.expert.common.exception.ErrorCode;
import org.example.expert.domain.todo.Todo;
import org.example.expert.domain.user.auth.Auth;
import org.example.expert.domain.user.auth.AuthUser;
import org.example.expert.common.exception.InvalidRequestException;
import org.example.expert.presentation.external.dto.request.TodoSaveRequestDto;
import org.example.expert.presentation.external.dto.response.TodoResponse;
import org.example.expert.application.service.TodoService;
import org.example.expert.presentation.external.mapper.TodoMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
            @Valid @RequestBody final TodoSaveRequestDto dto
    ) {
        Todo todo = todoService.saveTodo(authUser, dto);
        return ResponseEntity.ok(todoMapper.toDto(todo));
    }

    @GetMapping("/todos")
    public ResponseEntity<Page<TodoResponse>> getTodos(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = validatePageSize(page, size);
        Page<Todo> todo = todoService.getTodos(pageable);
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

    //helper
    private Pageable validatePageSize(int page, int size) {
        if (page < 1 || size < 1) {
            throw new InvalidRequestException(ErrorCode.PAGING_ERROR);
        }
        return PageRequest.of(page - 1, size, Sort.by("createdAt").descending());
    }
}
