package org.example.expert.application;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.user.auth.AuthUser;
import org.example.expert.domain.todo.*;
import org.example.expert.domain.todo.comment.*;
import org.example.expert.interfaces.dto.todo.CommentResponse;
import org.example.expert.interfaces.dto.todo.CommentSaveRequest;
import org.example.expert.interfaces.dto.todo.CommentSaveResponse;
import org.example.expert.interfaces.dto.user.UserResponse;
import org.example.expert.domain.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final TodoRepository todoRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public CommentSaveResponse saveComment(AuthUser authUser, long todoId, CommentSaveRequest commentSaveRequest) {
        User user = User.fromAuthUser(authUser);
        Todo todo = EntityFinder.findByIdOrThrow(todoRepository, todoId, "Todo not found");

        Comment newComment = new Comment(
                commentSaveRequest.getContents(),
                user,
                todo
        );

        Comment savedComment = commentRepository.save(newComment);

        return new CommentSaveResponse(
                savedComment.getId(),
                savedComment.getContents(),
                new UserResponse(user.getId(), user.getEmail())
        );
    }

    public List<CommentResponse> getComments(long todoId) {
        List<Comment> commentList = commentRepository.findAllByTodoId(todoId);

        List<CommentResponse> dtoList = new ArrayList<>();
        for (Comment comment : commentList) {
            User user = comment.getUser();
            CommentResponse dto = new CommentResponse(
                    comment.getId(),
                    comment.getContents(),
                    new UserResponse(user.getId(), user.getEmail())
            );
            dtoList.add(dto);
        }
        return dtoList;
    }
}
