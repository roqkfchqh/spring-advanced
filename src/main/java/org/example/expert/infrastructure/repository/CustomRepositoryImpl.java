package org.example.expert.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import org.example.expert.common.exception.base.ErrorCode;
import org.example.expert.common.exception.business.InvalidRequestException;
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

    //겹치는 repository 관련 코드들 이런식으로 함께 사용하고싶은데 의존성 주입할때 오류가 생기는 상황입니다
    //좋은 방법 있을까요..
}
