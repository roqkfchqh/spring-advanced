package org.example.expert.interfaces.external.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class ManagerSaveDto {

    @NotNull
    Long managerUserId;
}