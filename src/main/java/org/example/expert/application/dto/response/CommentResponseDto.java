package org.example.expert.application.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CommentResponseDto(Long id, String contents, UserResponse user) {

}
