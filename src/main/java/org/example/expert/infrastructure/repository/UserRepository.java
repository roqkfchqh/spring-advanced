package org.example.expert.infrastructure.repository;

import org.example.expert.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, CustomRepository<User> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
