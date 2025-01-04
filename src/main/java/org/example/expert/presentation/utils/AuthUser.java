package org.example.expert.presentation.utils;

import org.example.expert.domain.user.UserRole;

public record AuthUser(Long id, String email, UserRole userRole) {
}
