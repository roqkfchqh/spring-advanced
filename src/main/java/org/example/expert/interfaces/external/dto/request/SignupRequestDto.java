package org.example.expert.interfaces.external.dto.request;

import org.example.expert.domain.user.UserRole;

public record SignupRequestDto(String email, String password, UserRole userRole) {
}