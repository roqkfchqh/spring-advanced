package org.example.expert.interfaces.external.mapper;

//TODO mapper 라이브러리 찾아보기
public interface Mapper<E, S> {

    S toDto(E entity);
}
