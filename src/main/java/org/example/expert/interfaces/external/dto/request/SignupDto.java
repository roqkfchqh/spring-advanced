package org.example.expert.interfaces.external.dto.request;

import lombok.Value;
import org.example.expert.domain.user.UserRole;

import java.util.Arrays;

@Value
public class SignupDto {

    String email;
    String password;
    UserRole userRole;

    public SignupDto(String email, String password, String userRole) {
        this.email = email;
        this.password = password;
        this.userRole = validateAndConvert(userRole);

        validate();
    }

    private void validate() {
        if (email == null || !email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            throw new IllegalArgumentException("유효하지 않은 이메일 형식입니다.");
        }
        if (password == null || password.length() < 8 || !password.matches(".*\\d.*") || !password.matches(".*[A-Z].*")) {
            throw new IllegalArgumentException("비밀번호는 8자 이상이어야 하며, 숫자와 대문자를 포함해야 합니다.");
        }
        if (userRole == null) {
            throw new IllegalArgumentException("유저 역할은 필수입니다.");
        }
    }

    private UserRole validateAndConvert(String role) {
        if (role == null || role.isBlank()) {
            throw new IllegalArgumentException("UserRole은 비어 있을 수 없습니다.");
        }
        return Arrays.stream(UserRole.values())
                .filter(r -> r.name().equalsIgnoreCase(role))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 UserRole입니다."));
    }
}