package org.example.expert.interfaces.external.dto.request;

import lombok.Value;

@Value
public class SigninDto {

    String email;
    String password;

    public SigninDto(String email, String password) {
        this.email = email;
        this.password = password;

        validate();
    }

    private void validate() {
        if (email == null || !email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            throw new IllegalArgumentException("유효하지 않은 이메일 형식입니다.");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("비밀번호는 필수입니다.");
        }
    }
}
