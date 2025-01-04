package org.example.expert.presentation.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.expert.common.exception.ErrorCode;
import org.example.expert.presentation.utils.Auth;
import org.example.expert.presentation.utils.AuthUser;
import org.example.expert.common.exception.InvalidRequestException;
import org.example.expert.application.dto.request.TodoSaveRequestDto;
import org.example.expert.application.dto.response.TodoResponse;
import org.example.expert.application.service.TodoService;
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

    @PostMapping("/todos")
    public ResponseEntity<TodoResponse> saveTodo(
            @Auth AuthUser authUser,
            @Valid @RequestBody final TodoSaveRequestDto dto
    ) {
        TodoResponse todo = todoService.saveTodo(authUser, dto);
        return ResponseEntity.ok(todo);
    }

    @GetMapping("/todos")
    public ResponseEntity<Page<TodoResponse>> getTodos(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = validatePageSize(page, size);
        Page<TodoResponse> todo = todoService.getTodos(pageable);
        return ResponseEntity.ok(todo);
    }

    @GetMapping("/todos/{todoId}")
    public ResponseEntity<TodoResponse> getTodo(
            @PathVariable long todoId
    ) {
        TodoResponse todo = todoService.getTodo(todoId);
        return ResponseEntity.ok(todo);
    }

    //helper
    private Pageable validatePageSize(int page, int size) {
        if (page < 1 || size < 1) {
            throw new InvalidRequestException(ErrorCode.PAGING_ERROR);
        }
        return PageRequest.of(page - 1, size, Sort.by("createdAt").descending());
    }
}
