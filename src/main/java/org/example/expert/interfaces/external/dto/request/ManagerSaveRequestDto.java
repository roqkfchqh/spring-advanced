package org.example.expert.interfaces.external.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ManagerSaveRequestDto(@NotBlank Long managerUserId) {

}
