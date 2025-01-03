package org.example.expert.presentation.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.todo.comment.Comment;
import org.example.expert.presentation.external.dto.request.CommentSaveRequestDto;
import org.example.expert.presentation.external.dto.response.CommentResponseDto;
import org.example.expert.application.service.CommentService;
import org.example.expert.domain.user.auth.Auth;
import org.example.expert.domain.user.auth.AuthUser;
import org.example.expert.presentation.external.mapper.CommentMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final CommentMapper commentMapper;

    @PostMapping("/todos/{todoId}/comments")
    public ResponseEntity<CommentResponseDto> saveComment(
            @Auth AuthUser authUser,
            @PathVariable long todoId,
            @Valid @RequestBody final CommentSaveRequestDto dto
    ) {
        Comment comment = commentService.saveComment(authUser, todoId, dto);
        return ResponseEntity.ok(commentMapper.toDto(comment));
    }

    @GetMapping("/todos/{todoId}/comments")
    public ResponseEntity<List<CommentResponseDto>> getComments(
            @PathVariable long todoId
    ) {
        List<Comment> comments = commentService.getComments(todoId);
        return ResponseEntity.ok(comments
                .stream()
                .map(commentMapper::toDto)
                .toList()
        );
    }
}
