package org.example.expert.presentation.rest;

import lombok.RequiredArgsConstructor;
import org.example.expert.application.service.CommentAdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentAdminController {

    private final CommentAdminService commentAdminService;

    @DeleteMapping("/admin/comments/{commentId}")
    public ResponseEntity<String> deleteComment(
            @PathVariable long commentId
    ) {
        commentAdminService.deleteComment(commentId);
        return ResponseEntity.ok(commentId + "번 댓글이 정상적으로 삭제되었습니다.");
    }
}
