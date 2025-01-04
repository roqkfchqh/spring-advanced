package org.example.expert.application.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.expert.application.dto.response.ManagerResponse;
import org.example.expert.application.mapper.Mapper;
import org.example.expert.domain.todo.Manager;
import org.example.expert.infrastructure.repository.ManagerRepository;
import org.example.expert.infrastructure.repository.UserRepository;
import org.example.expert.common.exception.ErrorCode;
import org.example.expert.presentation.utils.AuthUser;
import org.example.expert.common.exception.InvalidRequestException;
import org.example.expert.domain.todo.Todo;
import org.example.expert.infrastructure.repository.TodoRepository;
import org.example.expert.domain.user.*;
import org.example.expert.application.dto.request.ManagerSaveRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
public class ManagerService {

    private final ManagerRepository managerRepository;
    private final UserRepository userRepository;
    private final TodoRepository todoRepository;
    private final Mapper<Manager, ManagerResponse> mapper;

    @Transactional
    public ManagerResponse saveManager(AuthUser authUser, long todoId, final ManagerSaveRequestDto dto) {
        Todo todo = todoRepository.findByIdOrThrow(todoId, ErrorCode.TODO_NOT_FOUND);

        if (todo.getUser() == null || !ObjectUtils.nullSafeEquals(authUser.id(), todo.getUser().getId())) {
            throw new InvalidRequestException(ErrorCode.TODO_USER_NOT_VALID);
        }

        User managerUser = userRepository.findByIdOrThrow(dto.managerUserId(), ErrorCode.USER_NOT_FOUND);

        if (ObjectUtils.nullSafeEquals(authUser.id(), dto.managerUserId())) {
            throw new InvalidRequestException(ErrorCode.USER_MANAGER_CANNOT);
        }

        Manager newManagerUser = new Manager(managerUser, todo);
        managerRepository.save(newManagerUser);

        return mapper.toDto(newManagerUser);
    }

    public List<ManagerResponse> getManagers(long todoId) {
        todoRepository.validateExistsById(todoId, ErrorCode.TODO_NOT_FOUND);
        List<Manager> manager = managerRepository.findAllByTodoId(todoId);
        return manager.stream()
                .map(mapper::toDto)
                .toList();
    }

    public void deleteManager(AuthUser authUser, long todoId, long managerId) {
        Todo todo = todoRepository.findByIdOrThrow(todoId, ErrorCode.TODO_NOT_FOUND);

        if (todo.getUser() == null || !ObjectUtils.nullSafeEquals(authUser.id(), todo.getUser().getId())) {
            throw new InvalidRequestException(ErrorCode.TODO_USER_NOT_VALID);
        }

        Manager manager = managerRepository.findByIdOrThrow(managerId, ErrorCode.MANAGER_NOT_FOUND);

        if (!ObjectUtils.nullSafeEquals(todo.getId(), manager.getTodo().getId())) {
            throw new InvalidRequestException(ErrorCode.TODO_MANAGER_NOT_VALID);
        }

        managerRepository.delete(manager);
    }
}
