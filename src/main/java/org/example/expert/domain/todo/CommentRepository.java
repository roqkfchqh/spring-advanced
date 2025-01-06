package org.example.expert.domain.todo;

import java.util.List;

import org.example.expert.infrastructure.repository.CustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long>, CustomRepository<Comment> {

    @Query("SELECT c FROM Comment c JOIN FETCH c.user JOIN FETCH c.todo WHERE c.todo.id = :todoId")
    List<Comment> findAllByTodoId(@Param("todoId") Long todoId);
}
