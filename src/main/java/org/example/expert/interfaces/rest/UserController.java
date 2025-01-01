package org.example.expert.interfaces.rest;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.user.User;
import org.example.expert.domain.user.auth.Auth;
import org.example.expert.domain.user.auth.AuthUser;
import org.example.expert.interfaces.external.dto.request.UserChangePasswordDto;
import org.example.expert.interfaces.external.dto.response.UserResponse;
import org.example.expert.application.service.UserService;
import org.example.expert.interfaces.external.mapper.UserMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserResponse> getUser(
            @PathVariable long userId
    ) {
        User user = userService.getUser(userId);
        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @PutMapping("/users")
    public void changePassword(
            @Auth AuthUser authUser,
            @RequestBody UserChangePasswordDto dto
    ) {
        userService.changePassword(authUser.getId(), dto);
    }
}
