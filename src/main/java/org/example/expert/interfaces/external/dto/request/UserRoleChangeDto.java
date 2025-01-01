package org.example.expert.interfaces.external.dto.request;

import lombok.Value;
import org.example.expert.domain.user.UserRole;

import java.util.Arrays;

@Value
public class UserRoleChangeDto {

    UserRole role;

    public UserRoleChangeDto(String role) {
        this.role = validateAndConvert(role);
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
