package org.example.expert.application.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ManagerSaveRequestDto(
        @NotBlank(message = "매니저로 등록할 userId를 입력해주세요.") Long managerUserId) {

}
