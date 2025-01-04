package org.example.expert.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import org.example.expert.common.exception.ErrorCode;
import org.example.expert.common.exception.InvalidRequestException;
import org.springframework.data.jpa.repository.JpaRepository;

@RequiredArgsConstructor
public class CustomRepositoryImpl<T> implements CustomRepository<T> {

    private final JpaRepository<T, Long> repository;

    @Override
    public T findByIdOrThrow(Long id, ErrorCode errorCode) {
        return repository.findById(id)
                .orElseThrow(() -> new InvalidRequestException(errorCode));
    }

    @Override
    public void validateExistsById(Long id, ErrorCode errorCode) {
        if (id == null) {
            throw new InvalidRequestException(ErrorCode.ID_NULL);
        }
        if (!repository.existsById(id)) {
            throw new InvalidRequestException(errorCode);
        }
    }
}
