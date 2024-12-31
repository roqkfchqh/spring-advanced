package org.example.expert.interfaces.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.todo.comment.Comment;
import org.example.expert.interfaces.external.dto.request.CommentSaveRequest;
import org.example.expert.interfaces.external.dto.response.CommentResponse;
import org.example.expert.application.service.CommentService;
import org.example.expert.domain.user.auth.Auth;
import org.example.expert.domain.user.auth.AuthUser;
import org.example.expert.interfaces.external.mapper.CommentMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final CommentMapper commentMapper;

    @PostMapping("/todos/{todoId}/comments")
    public ResponseEntity<CommentResponse> saveComment(
            @Auth AuthUser authUser,
            @PathVariable long todoId,
            @Valid @RequestBody CommentSaveRequest commentSaveRequest
    ) {
        Comment comment = commentService.saveComment(authUser, todoId, commentSaveRequest);
        return ResponseEntity.ok(commentMapper.toDto(comment));
    }

    @GetMapping("/todos/{todoId}/comments")
    public ResponseEntity<List<CommentResponse>> getComments(
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
