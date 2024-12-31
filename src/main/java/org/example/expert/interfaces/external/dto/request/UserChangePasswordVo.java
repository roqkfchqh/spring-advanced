package org.example.expert.interfaces.external.dto.request;

import lombok.Value;

@Value
public class UserChangePasswordVo {

    String oldPassword;
    String newPassword;

    public UserChangePasswordVo(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;

        validate();
    }

    private void validate() {
        if (oldPassword == null || oldPassword.isBlank()) {
            throw new IllegalArgumentException("현재 비밀번호는 필수입니다.");
        }
        if (newPassword == null || newPassword.isBlank()) {
            throw new IllegalArgumentException("새 비밀번호는 필수입니다.");
        }
        if(newPassword.equals(oldPassword)) {
            throw new IllegalArgumentException("현재 비밀번호와 새 비밀번호가 같을 수 없습니다.");
        }
        if (newPassword.length() < 8 || !newPassword.matches(".*\\d.*") || !newPassword.matches(".*[A-Z].*")) {
            throw new IllegalArgumentException("새 비밀번호는 8자 이상이어야 하며, 숫자와 대문자를 포함해야 합니다.");
        }
    }
}
