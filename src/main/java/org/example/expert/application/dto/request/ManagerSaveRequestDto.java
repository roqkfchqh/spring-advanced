package org.example.expert.application.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ManagerSaveRequestDto(@NotBlank Long managerUserId) {

}
