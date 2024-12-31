package org.example.expert.interfaces.external.mapper;

public interface Mapper<E, S> {

    S toDto(E entity);
}
