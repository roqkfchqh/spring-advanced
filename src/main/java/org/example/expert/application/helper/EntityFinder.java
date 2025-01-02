package org.example.expert.application.helper;

import org.example.expert.common.exception.InvalidRequestException;
import org.springframework.data.jpa.repository.JpaRepository;

public class EntityFinder {

    public static <T> T findByIdOrThrow(JpaRepository<T, Long> repository, Long id, String errorMessage) {
        return repository.findById(id)
                .orElseThrow(() -> new InvalidRequestException(errorMessage));
    }
}
