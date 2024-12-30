package org.example.expert.application;

import org.example.expert.infrastructure.exception.InvalidRequestException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public class EntityFinder {

    public static <T> T findByIdOrThrow(JpaRepository<T, Long> repository, Long id, String errorMessage) {
        return repository.findById(id)
                .orElseThrow(() -> new InvalidRequestException(errorMessage));
    }
}
