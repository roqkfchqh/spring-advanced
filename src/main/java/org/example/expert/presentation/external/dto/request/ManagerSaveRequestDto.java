package org.example.expert.presentation.external.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ManagerSaveRequestDto(@NotBlank Long managerUserId) {

}
