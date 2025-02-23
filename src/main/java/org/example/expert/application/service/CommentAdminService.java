package org.example.expert.application.service;

import lombok.RequiredArgsConstructor;
import org.example.expert.common.exception.base.ErrorCode;
import org.example.expert.domain.todo.CommentRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentAdminService {

    private final CommentRepository commentRepository;

    public void deleteComment(long commentId) {
        commentRepository.validateExistsById(commentId, ErrorCode.COMMENT_NOT_FOUND);
        commentRepository.deleteById(commentId);
    }
}
