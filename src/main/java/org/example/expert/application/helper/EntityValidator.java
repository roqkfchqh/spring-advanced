package org.example.expert.application.helper;

import org.example.expert.common.exception.ErrorCode;
import org.example.expert.common.exception.InvalidRequestException;
import org.springframework.data.jpa.repository.JpaRepository;

public class EntityValidator {

    public static <T> void isExistsById(JpaRepository<T, Long> repository, Long id, ErrorCode errorCode) {
        if(id == null){
            throw new InvalidRequestException(ErrorCode.ID_NULL);
        }
        if(!repository.existsById(id)) {
            throw new InvalidRequestException(errorCode);
        }
    }
}
