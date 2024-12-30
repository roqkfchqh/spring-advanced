package org.example.expert.interfaces;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.common.Auth;
import org.example.expert.domain.common.AuthUser;
import org.example.expert.domain.user.UserChangePasswordRequest;
import org.example.expert.domain.user.UserResponse;
import org.example.expert.application.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable long userId) {
        return ResponseEntity.ok(userService.getUser(userId));
    }

    @PutMapping("/users")
    public void changePassword(@Auth AuthUser authUser, @RequestBody UserChangePasswordRequest userChangePasswordRequest) {
        userService.changePassword(authUser.getId(), userChangePasswordRequest);
    }
}
