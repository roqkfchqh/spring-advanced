package org.example.expert.application.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record TodoResponse(Long id, String title, String contents, String weather, UserResponse user,
                           LocalDateTime createdAt, LocalDateTime modifiedAt) {

}
