package org.example.expert.domain.user.auth;

import lombok.Getter;
import org.example.expert.domain.user.Email;
import org.example.expert.domain.user.UserRole;

@Getter
public class AuthUser {

    private final Long id;
    private final Email email;
    private final UserRole userRole;

    public AuthUser(Long id, Email email, UserRole userRole) {
        this.id = id;
        this.email = email;
        this.userRole = userRole;
    }
}
