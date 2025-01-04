package org.example.expert.application.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ManagerResponse(Long id, UserResponse user) {

}
