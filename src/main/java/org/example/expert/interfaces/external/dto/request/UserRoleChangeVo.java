package org.example.expert.interfaces.external.dto.request;

import lombok.Value;
import org.example.expert.domain.user.UserRole;

import java.util.Arrays;

@Value
public class UserRoleChangeVo {

    UserRole role;

    public UserRoleChangeVo(String role) {
        this.role = validateAndConvert(role);
    }

    private UserRole validateAndConvert(String role) {
        return Arrays.stream(UserRole.values())
                .filter(r -> r.name().equalsIgnoreCase(role))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 UserRole입니다."));
    }
}
