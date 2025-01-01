package org.example.expert.domain.comment.service;

import org.example.expert.application.service.CommentService;
import org.example.expert.domain.user.auth.AuthUser;
import org.example.expert.infrastructure.exception.InvalidRequestException;
import org.example.expert.infrastructure.exception.ServerException;
import org.example.expert.domain.todo.*;
import org.example.expert.domain.todo.comment.Comment;
import org.example.expert.domain.todo.comment.CommentRepository;
import org.example.expert.interfaces.external.dto.request.CommentSaveVo;
import org.example.expert.domain.user.User;
import org.example.expert.domain.user.UserRole;
import org.example.expert.interfaces.external.dto.response.CommentResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;
    @Mock
    private TodoRepository todoRepository;
    @InjectMocks
    private CommentService commentService;

    @Test
    public void todoNotFoundWhenCreateComment() {
        // given
        long todoId = 1;
        CommentSaveVo request = new CommentSaveVo("contents");
        AuthUser authUser = new AuthUser(1L, "email", UserRole.USER);

        given(todoRepository.findById(anyLong())).willReturn(Optional.empty());

        // when
        InvalidRequestException exception = assertThrows(InvalidRequestException.class, () -> {
            commentService.saveComment(authUser, todoId, request);
        });

        // then
        assertEquals("Todo not found", exception.getMessage());
    }

    @Test
    public void successCreateComment() {
        // given
        long todoId = 1;
        CommentSaveVo request = new CommentSaveVo("contents");
        AuthUser authUser = new AuthUser(1L, "email", UserRole.USER);
        User user = User.fromAuthUser(authUser);
        Todo todo = new Todo("title", "title", "contents", user);
        Comment comment = new Comment(request.getContents(), user, todo);

        given(todoRepository.findById(anyLong())).willReturn(Optional.of(todo));
        given(commentRepository.save(any())).willReturn(comment);

        // when
        Comment result = commentService.saveComment(authUser, todoId, request);

        // then
        assertNotNull(result);
        assertEquals(request.getContents(), result.getContents());
        assertEquals(user.getId(), result.getUser().getId());
        assertEquals(todo.getId(), result.getTodo().getId());
        assertEquals(user.getEmail(), result.getUser().getEmail());
    }
}
