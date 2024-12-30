package org.example.expert.application.tokenprovider;

import org.example.expert.domain.user.Email;
import org.example.expert.domain.user.UserRole;

public interface TokenProvider {

    String createToken(Long userId, Email email, UserRole userRole);
}
