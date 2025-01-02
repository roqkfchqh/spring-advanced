package org.example.expert.presentation.external.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ManagerResponse(Long id, UserResponse user) {

}
