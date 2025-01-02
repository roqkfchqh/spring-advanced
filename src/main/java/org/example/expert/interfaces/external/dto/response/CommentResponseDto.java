package org.example.expert.interfaces.external.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@RequiredArgsConstructor
public class CommentResponseDto {

    private final Long id;
    private final String contents;
    private final UserResponse user;
}
