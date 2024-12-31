package org.example.expert.application.helper;

import org.example.expert.infrastructure.exception.InvalidRequestException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public class EntityValidator {

    public static <T> void isExistsById(JpaRepository<T, Long> repository, Long id, String errorMessage) {
        if(id == null){
            throw new InvalidRequestException("id is null");
        }
        if(!repository.existsById(id)) {
            throw new InvalidRequestException(errorMessage);
        }
    }
}
