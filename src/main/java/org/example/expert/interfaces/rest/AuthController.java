package org.example.expert.interfaces.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.expert.application.manager.AuthManager;
import org.example.expert.interfaces.external.dto.request.SigninRequest;
import org.example.expert.interfaces.external.dto.request.SignupRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthManager authManager;

    @PostMapping("/auth/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody SignupRequest signupRequest) {
        String token = authManager.handleSignup(signupRequest);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/auth/signin")
    public ResponseEntity<String> signin(@Valid @RequestBody SigninRequest signinRequest) {
        String token = authManager.handleSignin(signinRequest);
        return ResponseEntity.ok(token);
    }
}
