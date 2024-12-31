package org.example.expert.interfaces.rest;

import lombok.RequiredArgsConstructor;
import org.example.expert.application.service.CommentAdminService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentAdminController {

    private final CommentAdminService commentAdminService;

    @DeleteMapping("/admin/comments/{commentId}")
    public void deleteComment(
            @PathVariable long commentId
    ) {
        commentAdminService.deleteComment(commentId);
    }
}
