package org.example.expert.presentation.rest;

import lombok.RequiredArgsConstructor;
import org.example.expert.presentation.utils.Auth;
import org.example.expert.presentation.utils.AuthUser;
import org.example.expert.application.dto.request.UserChangePasswordRequestDto;
import org.example.expert.application.dto.response.UserResponse;
import org.example.expert.application.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserResponse> getUser(
            @PathVariable long userId
    ) {
        UserResponse user = userService.getUser(userId);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/users")
    public ResponseEntity<String> changePassword(
            @Auth AuthUser authUser,
            @RequestBody UserChangePasswordRequestDto dto
    ) {
        userService.changePassword(authUser, dto);
        return ResponseEntity.ok("비밀번호가 변경되었습니다.");
    }
}
