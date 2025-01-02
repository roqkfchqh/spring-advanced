package org.example.expert.application.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.expert.application.helper.EntityFinder;
import org.example.expert.common.exception.ErrorCode;
import org.example.expert.domain.user.auth.AuthUser;
import org.example.expert.common.exception.InvalidRequestException;
import org.example.expert.domain.todo.Todo;
import org.example.expert.domain.todo.TodoRepository;
import org.example.expert.domain.user.*;
import org.example.expert.domain.user.manager.*;
import org.example.expert.presentation.external.dto.request.ManagerSaveRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
public class ManagerService {

    private final ManagerRepository managerRepository;
    private final UserRepository userRepository;
    private final TodoRepository todoRepository;

    @Transactional
    public Manager saveManager(AuthUser authUser, long todoId, final ManagerSaveRequestDto dto) {
        User user = User.fromAuthUser(authUser);
        Todo todo = EntityFinder.findByIdOrThrow(todoRepository, todoId, ErrorCode.TODO_NOT_FOUND);

        if (todo.getUser() == null || !ObjectUtils.nullSafeEquals(user.getId(), todo.getUser().getId())) {
            throw new InvalidRequestException(ErrorCode.USER_NOT_VALID);
        }

        User managerUser = EntityFinder.findByIdOrThrow(userRepository, dto.managerUserId(), ErrorCode.MANAGER_NOT_FOUND);

        if (ObjectUtils.nullSafeEquals(user.getId(), managerUser.getId())) {
            throw new InvalidRequestException(ErrorCode.USER_MANAGER_CANNOT);
        }

        Manager newManagerUser = new Manager(managerUser, todo);

        return managerRepository.save(newManagerUser);
    }

    public List<Manager> getManagers(long todoId) {
        Todo todo = EntityFinder.findByIdOrThrow(todoRepository, todoId, ErrorCode.TODO_NOT_FOUND);

        return managerRepository.findAllByTodoId(todo.getId());
    }

    public void deleteManager(long userId, long todoId, long managerId) {
        User user = EntityFinder.findByIdOrThrow(userRepository, userId, ErrorCode.USER_NOT_FOUND);
        Todo todo = EntityFinder.findByIdOrThrow(todoRepository, todoId, ErrorCode.TODO_NOT_FOUND);

        if (todo.getUser() == null || !ObjectUtils.nullSafeEquals(user.getId(), todo.getUser().getId())) {
            throw new InvalidRequestException(ErrorCode.USER_NOT_VALID);
        }

        Manager manager = EntityFinder.findByIdOrThrow(managerRepository, managerId, ErrorCode.MANAGER_NOT_FOUND);

        if (!ObjectUtils.nullSafeEquals(todo.getId(), manager.getTodo().getId())) {
            throw new InvalidRequestException(ErrorCode.TODO_MANAGER_VALID);
        }

        managerRepository.delete(manager);
    }
}
