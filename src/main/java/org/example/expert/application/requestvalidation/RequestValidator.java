package org.example.expert.application.requestvalidation;

import org.example.expert.domain.user.UserRole;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class RequestValidator {

    public void userRole(UserRole userRole) {
        Arrays.stream(UserRole.values())
                .filter(r -> r.name().equalsIgnoreCase(String.valueOf(userRole)))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 UserRole입니다."));
    }

    public void password(String password) {
        if (password == null || password.length() < 8 || !password.matches(".*\\d.*") || !password.matches(".*[A-Z].*")) {
            throw new IllegalArgumentException("비밀번호는 8자 이상이어야 하며, 숫자와 대문자를 포함해야 합니다.");
        }
    }

    public void email(String email) {
        if (email == null || !email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            throw new IllegalArgumentException("유효하지 않은 이메일 형식입니다.");
        }
    }

    public void isPasswordNull(String password) {
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("비밀번호는 필수입니다.");
        }
    }

    public void isUserRoleNull(UserRole userRole) {
        if (userRole == null) {
            throw new IllegalArgumentException("유저 역할은 필수입니다.");
        }
    }

    public void changePassword(String oldPassword, String newPassword) {
        if (oldPassword == null || oldPassword.isBlank()) {
            throw new IllegalArgumentException("현재 비밀번호는 필수입니다.");
        }
        if (newPassword == null || newPassword.isBlank()) {
            throw new IllegalArgumentException("새 비밀번호는 필수입니다.");
        }
        if(newPassword.equals(oldPassword)) {
            throw new IllegalArgumentException("현재 비밀번호와 새 비밀번호가 같을 수 없습니다.");
        }
    }
}
