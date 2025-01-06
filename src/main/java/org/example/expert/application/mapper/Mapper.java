package org.example.expert.application.mapper;

public interface Mapper<E, S> {

    //entity->dto만 mapper 사용
    S toDto(E entity);
}
