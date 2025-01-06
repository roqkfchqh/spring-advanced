package org.example.expert.application.manager;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.user.UserRole;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidationService {//검증 로직 많이 필요한 경우 모아둠

    private final RequestValidator requestValidator;

    public void validateSignup(String email, String password, UserRole userRole) {
        requestValidator.email(email);
        requestValidator.password(password);
        requestValidator.userRole(userRole);
    }

    public void validateSignin(String email, String password) {
        requestValidator.password(password);
        requestValidator.email(email);
    }

    public void validateChangePassword(String oldPassword, String newPassword) {
        requestValidator.changePassword(oldPassword, newPassword);
    }

    public void validateUserRole(UserRole userRole) {
        requestValidator.userRole(userRole);
    }
}
