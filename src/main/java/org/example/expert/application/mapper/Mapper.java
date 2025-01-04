package org.example.expert.application.mapper;

public interface Mapper<E, S> {

    S toDto(E entity);
}
