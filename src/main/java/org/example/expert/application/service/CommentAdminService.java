package org.example.expert.application.service;

import lombok.RequiredArgsConstructor;
import org.example.expert.application.helper.EntityValidator;
import org.example.expert.domain.todo.comment.CommentRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentAdminService {

    private final CommentRepository commentRepository;

    public void deleteComment(long commentId) {
        EntityValidator.isExistsById(commentRepository, commentId, "Comment not found");
        commentRepository.deleteById(commentId);
    }
}
