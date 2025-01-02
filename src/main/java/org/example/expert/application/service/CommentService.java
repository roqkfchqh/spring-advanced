package org.example.expert.application.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.expert.application.helper.EntityFinder;
import org.example.expert.application.helper.EntityValidator;
import org.example.expert.domain.user.auth.AuthUser;
import org.example.expert.domain.todo.*;
import org.example.expert.domain.todo.comment.*;
import org.example.expert.interfaces.external.dto.request.CommentSaveRequestDto;
import org.example.expert.domain.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final TodoRepository todoRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public Comment saveComment(AuthUser authUser, long todoId, final CommentSaveRequestDto dto) {
        User user = User.fromAuthUser(authUser);
        Todo todo = EntityFinder.findByIdOrThrow(todoRepository, todoId, "Todo not found");

        Comment newComment = new Comment(
                dto.contents(),
                user,
                todo
        );

        return commentRepository.save(newComment);
    }

    public List<Comment> getComments(long todoId) {
        EntityValidator.isExistsById(todoRepository, todoId, "Todo not found");
        return commentRepository.findAllByTodoId(todoId);
    }
}
