package org.example.expert.application.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.expert.application.helper.EntityFinder;
import org.example.expert.domain.user.auth.AuthUser;
import org.example.expert.infrastructure.exception.InvalidRequestException;
import org.example.expert.domain.todo.Todo;
import org.example.expert.domain.todo.TodoRepository;
import org.example.expert.domain.user.*;
import org.example.expert.domain.user.manager.*;
import org.example.expert.interfaces.external.dto.request.ManagerSaveRequest;
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
    public Manager saveManager(AuthUser authUser, long todoId, ManagerSaveRequest managerSaveRequest) {
        // 일정을 만든 유저
        User user = User.fromAuthUser(authUser);
        Todo todo = EntityFinder.findByIdOrThrow(todoRepository, todoId, "Todo not found");

        if (!ObjectUtils.nullSafeEquals(user.getId(), todo.getUser().getId())) {
            throw new InvalidRequestException("담당자를 등록하려고 하는 유저가 일정을 만든 유저가 유효하지 않습니다.");
        }

        User managerUser = EntityFinder.findByIdOrThrow(userRepository, managerSaveRequest.getManagerUserId(), "Manager not found");

        if (ObjectUtils.nullSafeEquals(user.getId(), managerUser.getId())) {
            throw new InvalidRequestException("일정 작성자는 본인을 담당자로 등록할 수 없습니다.");
        }

        Manager newManagerUser = new Manager(managerUser, todo);

        return managerRepository.save(newManagerUser);
    }

    public List<Manager> getManagers(long todoId) {
        Todo todo = EntityFinder.findByIdOrThrow(todoRepository, todoId, "Todo not found");

        return managerRepository.findAllByTodoId(todo.getId());
    }

    public void deleteManager(long userId, long todoId, long managerId) {
        User user = EntityFinder.findByIdOrThrow(userRepository, userId, "User not found");
        Todo todo = EntityFinder.findByIdOrThrow(todoRepository, todoId, "Todo not found");

        if (todo.getUser() == null || !ObjectUtils.nullSafeEquals(user.getId(), todo.getUser().getId())) {
            throw new InvalidRequestException("해당 일정을 만든 유저가 유효하지 않습니다.");
        }

        Manager manager = EntityFinder.findByIdOrThrow(managerRepository, managerId, "Manager not found");

        if (!ObjectUtils.nullSafeEquals(todo.getId(), manager.getTodo().getId())) {
            throw new InvalidRequestException("해당 일정에 등록된 담당자가 아닙니다.");
        }

        managerRepository.delete(manager);
    }
}
