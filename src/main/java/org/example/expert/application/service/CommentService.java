package org.example.expert.application.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.expert.application.dto.response.CommentResponseDto;
import org.example.expert.application.mapper.Mapper;
import org.example.expert.infrastructure.repository.CommentRepository;
import org.example.expert.infrastructure.repository.TodoRepository;
import org.example.expert.common.exception.ErrorCode;
import org.example.expert.presentation.utils.AuthUser;
import org.example.expert.domain.todo.*;
import org.example.expert.application.dto.request.CommentSaveRequestDto;
import org.example.expert.domain.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final TodoRepository todoRepository;
    private final CommentRepository commentRepository;
    private final Mapper<Comment, CommentResponseDto> mapper;

    @Transactional
    public CommentResponseDto saveComment(AuthUser authUser, long todoId, final CommentSaveRequestDto dto) {
        User user = User.fromAuthUser(authUser);
        Todo todo = todoRepository.findByIdOrThrow(todoId, ErrorCode.TODO_NOT_FOUND);
        Comment comment = Comment.of(dto.contents(), user, todo);

        commentRepository.save(comment);
        return mapper.toDto(comment);
    }

    public List<CommentResponseDto> getComments(long todoId) {
        commentRepository.validateExistsById(todoId, ErrorCode.TODO_NOT_FOUND);

        List<Comment> comments = commentRepository.findAllByTodoId(todoId);
        return comments.stream()
                .map(mapper::toDto)
                .toList();
    }
}
