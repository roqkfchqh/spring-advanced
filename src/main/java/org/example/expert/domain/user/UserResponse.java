package org.example.expert.domain.user;

import lombok.Getter;

@Getter
public class UserResponse {

    private final Long id;
    private final Email email;

    public UserResponse(Long id, Email email) {
        this.id = id;
        this.email = email;
    }
}
