package org.example.expert.domain.manager.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.lenient;

import java.util.List;

import org.example.expert.application.dto.response.ManagerResponse;
import org.example.expert.application.dto.response.UserResponse;
import org.example.expert.application.mapper.Mapper;
import org.example.expert.common.exception.base.ErrorCode;
import org.example.expert.domain.todo.Manager;
import org.example.expert.domain.todo.ManagerRepository;
import org.example.expert.domain.user.UserRepository;
import org.example.expert.application.service.ManagerService;
import org.example.expert.presentation.utils.AuthUser;
import org.example.expert.common.exception.business.InvalidRequestException;
import org.example.expert.domain.todo.Todo;
import org.example.expert.domain.todo.TodoRepository;
import org.example.expert.domain.user.*;
import org.example.expert.application.dto.request.ManagerSaveRequestDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class ManagerServiceTest {

    @Mock
    private ManagerRepository managerRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private TodoRepository todoRepository;
    @InjectMocks
    private ManagerService managerService;
    @Mock
    @Qualifier("managerMapper")
    private Mapper<Manager, ManagerResponse> mapper;

    @Test
    public void todoNotFoundWhenGetManagers() {
        // given
        long todoId = 1L;

        doThrow(new InvalidRequestException(ErrorCode.TODO_NOT_FOUND))
                .when(todoRepository).validateExistsById(todoId, ErrorCode.TODO_NOT_FOUND);

        // when & then
        InvalidRequestException exception = assertThrows(InvalidRequestException.class, () -> {
            managerService.getManagers(todoId);
        });
        assertEquals("해당 게시글이 존재하지 않습니다.", exception.getMessage());
    }

    @Test
    void todoUserIsNull() {
        // given
        AuthUser authUser = new AuthUser(1L, "a@a.com", UserRole.USER);
        long todoId = 1L;
        long managerUserId = 2L;

        Todo todo = new Todo();
        ReflectionTestUtils.setField(todo, "user", null);

        ManagerSaveRequestDto managerSaveRequest = new ManagerSaveRequestDto(managerUserId);

        given(todoRepository.findByIdOrThrow(todoId, ErrorCode.TODO_NOT_FOUND)).willReturn(todo);

        // when & then
        InvalidRequestException exception = assertThrows(InvalidRequestException.class, () -> {
            managerService.saveManager(authUser, todoId, managerSaveRequest);
        });
        assertEquals("일정 작성자가 아닙니다.", exception.getMessage());
    }

    @Test
    public void successGetManagers() {
        // given
        long todoId = 1L;
        User user = User.of("user@example.com", "password", UserRole.USER);
        Todo todo = Todo.of("Test Title", "Test Content", "Sunny", user);
        Manager manager = Manager.of(user, todo);
        ManagerResponse responseDto = new ManagerResponse(
                manager.getId(),
                new UserResponse(user.getId(), user.getEmail())
        );

        lenient().when(todoRepository.findByIdOrThrow(todoId, ErrorCode.TODO_NOT_FOUND)).thenReturn(todo);
        lenient().when(managerRepository.findAllByTodoId(todoId)).thenReturn(List.of(manager));
        lenient().when(mapper.toDto(any(Manager.class))).thenReturn(responseDto);

        // when
        List<ManagerResponse> result = managerService.getManagers(todoId);

        // then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(manager.getId(), result.get(0).id());
        assertEquals(user.getEmail(), result.get(0).user().email());
    }

    @Test
    void successCreateTodo() {
        // given
        AuthUser authUser = new AuthUser(1L, "a@a.com", UserRole.USER);
        User user = User.fromAuthUser(authUser);

        long todoId = 1L;
        Todo todo = Todo.of("Test Title", "Test Contents", "Sunny", user);

        long managerUserId = 2L;
        User managerUser = User.of("b@b.com", "password", UserRole.USER);
        ReflectionTestUtils.setField(managerUser, "id", managerUserId);

        ManagerSaveRequestDto managerSaveRequest = new ManagerSaveRequestDto(managerUserId);
        ManagerResponse responseDto = new ManagerResponse(managerUserId, new UserResponse(managerUserId, managerUser.getEmail()));

        lenient().when(todoRepository.findByIdOrThrow(todoId, ErrorCode.TODO_NOT_FOUND)).thenReturn(todo);
        lenient().when(userRepository.findByIdOrThrow(managerUserId, ErrorCode.USER_NOT_FOUND)).thenReturn(managerUser);
        lenient().when(managerRepository.save(any(Manager.class))).thenAnswer(invocation -> invocation.getArgument(0));
        lenient().when(mapper.toDto(any(Manager.class))).thenReturn(responseDto);

        // when
        ManagerResponse response = managerService.saveManager(authUser, todoId, managerSaveRequest);

        // then
        assertNotNull(response);
        assertEquals(managerUser.getId(), response.user().id());
        assertEquals(managerUser.getEmail(), response.user().email());
    }
}
