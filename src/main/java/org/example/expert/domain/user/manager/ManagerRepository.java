package org.example.expert.domain.user.manager;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ManagerRepository extends JpaRepository<Manager, Long> {

    @Query("SELECT m FROM Manager m JOIN FETCH m.user JOIN FETCH m.todo WHERE m.todo.id = :todoId")
    List<Manager> findAllByTodoId(@Param("todoId") Long todoId);
}
