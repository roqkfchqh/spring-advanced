package org.example.expert.domain.comment.service;

import org.example.expert.application.dto.response.CommentResponseDto;
import org.example.expert.application.dto.response.UserResponse;
import org.example.expert.application.mapper.Mapper;
import org.example.expert.common.exception.base.ErrorCode;
import org.example.expert.domain.todo.TodoRepository;
import org.example.expert.application.service.CommentService;
import org.example.expert.presentation.utils.AuthUser;
import org.example.expert.common.exception.business.InvalidRequestException;
import org.example.expert.domain.todo.*;
import org.example.expert.domain.todo.Comment;
import org.example.expert.domain.todo.CommentRepository;
import org.example.expert.application.dto.request.CommentSaveRequestDto;
import org.example.expert.domain.user.User;
import org.example.expert.domain.user.UserRole;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Qualifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;
    @Mock
    private TodoRepository todoRepository;
    @InjectMocks
    private CommentService commentService;

    @Mock
    @Qualifier("commentMapper")
    private Mapper<Comment, CommentResponseDto> mapper;

    @Test
    public void todoNotFoundWhenGetComments() {
        // given
        long todoId = 1L;

        doThrow(new InvalidRequestException(ErrorCode.TODO_NOT_FOUND))
                .when(commentRepository).validateExistsById(todoId, ErrorCode.TODO_NOT_FOUND);

        // when & then
        InvalidRequestException exception = assertThrows(InvalidRequestException.class, () -> {
            commentService.getComments(todoId);
        });
        assertEquals("해당 게시글이 존재하지 않습니다.", exception.getMessage());
    }

    @Test
    public void successCreateComment() {
        // given
        long todoId = 1L;
        CommentSaveRequestDto request = new CommentSaveRequestDto("Test comment");
        AuthUser authUser = new AuthUser(1L, "email@test.com", UserRole.USER);
        User user = User.fromAuthUser(authUser);
        Todo todo = Todo.of("Test title", "Test contents", "Sunny", user);
        Comment comment = Comment.of(request.contents(), user, todo);
        CommentResponseDto responseDto = new CommentResponseDto(
                comment.getId(),
                comment.getContents(),
                new UserResponse(user.getId(), user.getEmail())
        );

        lenient().when(todoRepository.findByIdOrThrow(todoId, ErrorCode.TODO_NOT_FOUND)).thenReturn(todo);
        lenient().when(commentRepository.save(any(Comment.class))).thenReturn(comment);
        lenient().when(mapper.toDto(any(Comment.class))).thenReturn(responseDto);

        // when
        CommentResponseDto result = commentService.saveComment(authUser, todoId, request);

        // then
        assertNotNull(result);
        assertEquals(request.contents(), result.contents());
        assertEquals(user.getId(), result.user().id());
        assertEquals(user.getEmail(), result.user().email());
    }
}
