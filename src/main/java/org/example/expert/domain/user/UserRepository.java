package org.example.expert.domain.user;

import org.example.expert.infrastructure.repository.CustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, CustomRepository<User> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
