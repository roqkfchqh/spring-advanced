package org.example.expert.presentation.external.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CommentSaveRequestDto(@NotBlank String contents) {

}
