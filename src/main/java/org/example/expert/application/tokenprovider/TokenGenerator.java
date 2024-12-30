package org.example.expert.application.tokenprovider;

import org.example.expert.domain.user.Email;
import org.example.expert.domain.user.UserRole;

public interface TokenGenerator {

    String generate(Long userId, Email email, UserRole userRole);
}
