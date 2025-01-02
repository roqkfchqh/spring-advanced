package org.example.expert.application.helper;

import org.example.expert.common.exception.ErrorCode;
import org.example.expert.common.exception.InvalidRequestException;
import org.springframework.data.jpa.repository.JpaRepository;

public class EntityFinder {

    public static <T> T findByIdOrThrow(JpaRepository<T, Long> repository, Long id, ErrorCode errorCode) {
        return repository.findById(id)
                .orElseThrow(() -> new InvalidRequestException(errorCode));
    }
}
