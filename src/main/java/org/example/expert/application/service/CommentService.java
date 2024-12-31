package org.example.expert.application.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.expert.application.helper.EntityFinder;
import org.example.expert.domain.user.auth.AuthUser;
import org.example.expert.domain.todo.*;
import org.example.expert.domain.todo.comment.*;
import org.example.expert.interfaces.external.dto.request.CommentSaveRequest;
import org.example.expert.domain.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final TodoRepository todoRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public Comment saveComment(AuthUser authUser, long todoId, CommentSaveRequest commentSaveRequest) {
        User user = User.fromAuthUser(authUser);
        Todo todo = EntityFinder.findByIdOrThrow(todoRepository, todoId, "Todo not found");

        Comment newComment = new Comment(
                commentSaveRequest.getContents(),
                user,
                todo
        );

        return commentRepository.save(newComment);
    }

    //TODO: todoId 통해서 해당 Todo 존재하는지 체킹
    public List<Comment> getComments(long todoId) {
        return commentRepository.findAllByTodoId(todoId);
    }
}
