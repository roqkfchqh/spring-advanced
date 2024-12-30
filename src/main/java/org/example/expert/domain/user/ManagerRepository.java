package org.example.expert.domain.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<Manager, Long> {

    List<Manager> findAllByTodoId(Long todoId);
}
