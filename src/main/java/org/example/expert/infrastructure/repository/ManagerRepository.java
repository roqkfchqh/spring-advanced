package org.example.expert.infrastructure.repository;

import java.util.List;

import org.example.expert.domain.todo.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ManagerRepository extends JpaRepository<Manager, Long>, CustomRepository<Manager> {

    @Query("SELECT m FROM Manager m JOIN FETCH m.user JOIN FETCH m.todo WHERE m.todo.id = :todoId")
    List<Manager> findAllByTodoId(@Param("todoId") Long todoId);
}
