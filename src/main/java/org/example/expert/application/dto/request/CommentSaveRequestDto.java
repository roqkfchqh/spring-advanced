package org.example.expert.application.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CommentSaveRequestDto(@NotBlank String contents) {

}
