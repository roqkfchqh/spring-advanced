package org.example.expert.presentation.external.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CommentResponseDto(Long id, String contents, UserResponse user) {

}
