package org.example.expert.interfaces.dto.user;

import lombok.Getter;
import org.example.expert.domain.user.Email;

@Getter
public class UserResponse {

    private final Long id;
    private final Email email;

    public UserResponse(Long id, Email email) {
        this.id = id;
        this.email = email;
    }
}
