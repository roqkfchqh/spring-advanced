package org.example.expert.presentation.external.mapper;

public interface Mapper<E, S> {

    S toDto(E entity);
}
