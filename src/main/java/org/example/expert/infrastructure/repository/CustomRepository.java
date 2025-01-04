package org.example.expert.infrastructure.repository;

import org.example.expert.common.exception.ErrorCode;

public interface CustomRepository<T> {

    T findByIdOrThrow(Long id, ErrorCode errorCode);
    void validateExistsById(Long id, ErrorCode errorCode);
}
