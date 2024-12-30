package org.example.expert.domain.user.manager;

import lombok.Getter;
import org.example.expert.domain.user.UserResponse;

@Getter
public class ManagerResponse {

    private final Long id;
    private final UserResponse user;

    public ManagerResponse(Long id, UserResponse user) {
        this.id = id;
        this.user = user;
    }
}
