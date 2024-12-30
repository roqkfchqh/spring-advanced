package org.example.expert.domain.user.manager;

import lombok.Getter;
import org.example.expert.domain.user.UserResponse;

@Getter
public class ManagerSaveResponse {

    private final Long id;
    private final UserResponse user;

    public ManagerSaveResponse(Long id, UserResponse user) {
        this.id = id;
        this.user = user;
    }
}
