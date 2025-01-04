package org.example.expert.application.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CommentSaveRequestDto(
        @NotBlank(message = "댓글을 입력해주세요.") String contents) {

}
