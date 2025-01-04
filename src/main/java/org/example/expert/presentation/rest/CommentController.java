package org.example.expert.presentation.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.expert.application.dto.request.CommentSaveRequestDto;
import org.example.expert.application.dto.response.CommentResponseDto;
import org.example.expert.application.service.CommentService;
import org.example.expert.presentation.utils.Auth;
import org.example.expert.presentation.utils.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/todos/{todoId}/comments")
    public ResponseEntity<CommentResponseDto> saveComment(
            @Auth AuthUser authUser,
            @PathVariable long todoId,
            @Valid @RequestBody final CommentSaveRequestDto dto
    ) {
        CommentResponseDto comment = commentService.saveComment(authUser, todoId, dto);
        return ResponseEntity.ok(comment);
    }

    @GetMapping("/todos/{todoId}/comments")
    public ResponseEntity<List<CommentResponseDto>> getComments(
            @PathVariable long todoId
    ) {
        List<CommentResponseDto> comments = commentService.getComments(todoId);
        return ResponseEntity.ok(comments);
    }
}
