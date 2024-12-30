package org.example.expert.domain.user.manager;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ManagerSaveRequest {

    @NotNull
    private Long managerUserId;
}
