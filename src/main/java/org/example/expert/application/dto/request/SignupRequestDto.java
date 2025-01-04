package org.example.expert.application.dto.request;

import org.example.expert.domain.user.UserRole;

public record SignupRequestDto(String email, String password, UserRole userRole) {
}