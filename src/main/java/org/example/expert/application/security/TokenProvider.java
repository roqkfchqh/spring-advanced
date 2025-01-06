package org.example.expert.application.security;

import org.example.expert.domain.user.UserRole;

public interface TokenProvider {

    //token발급
    String createToken(Long userId, String email, UserRole userRole);
}
