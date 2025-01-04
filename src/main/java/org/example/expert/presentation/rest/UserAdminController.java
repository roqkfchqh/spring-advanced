package org.example.expert.presentation.rest;

import lombok.RequiredArgsConstructor;
import org.example.expert.application.manager.ValidateManager;
import org.example.expert.presentation.external.dto.request.UserRoleChangeRequestDto;
import org.example.expert.application.service.UserAdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserAdminController {

    private final UserAdminService userAdminService;
    private final ValidateManager validateManager;

    @PatchMapping("/admin/users/{userId}")
    public ResponseEntity<String> changeUserRole(
            @PathVariable long userId,
            @RequestBody UserRoleChangeRequestDto dto
    ) {
        validateManager.validateUserRole(dto.userRole());

        userAdminService.changeUserRole(userId, dto);
        return ResponseEntity.ok(userId + "번 회원의 역할이 " + dto.userRole() + "로 변경되었습니다.");
    }
}
