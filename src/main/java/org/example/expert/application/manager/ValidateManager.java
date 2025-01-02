package org.example.expert.application.manager;

import lombok.RequiredArgsConstructor;
import org.example.expert.application.requestvalidation.RequestValidator;
import org.example.expert.domain.user.UserRole;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidateManager {

    private final RequestValidator requestValidator;

    public void validateSignup(String email, String password, UserRole userRole) {
        requestValidator.isPasswordNull(password);
        requestValidator.isUserRoleNull(userRole);
        requestValidator.email(email);
        requestValidator.password(password);
        requestValidator.userRole(userRole);
    }

    public void validateSignin(String email, String password) {
        requestValidator.email(email);
        requestValidator.isPasswordNull(password);
    }

    public void validateChangePassword(String oldPassword, String newPassword) {
        requestValidator.isPasswordNull(oldPassword);
        requestValidator.isPasswordNull(newPassword);
        requestValidator.changePassword(oldPassword, newPassword);
    }

    public void validateUserRole(UserRole userRole) {
        requestValidator.isUserRoleNull(userRole);
        requestValidator.userRole(userRole);
    }
}
