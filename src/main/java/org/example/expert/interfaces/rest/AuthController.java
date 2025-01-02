package org.example.expert.interfaces.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.expert.application.manager.AuthManager;
import org.example.expert.application.manager.ValidateManager;
import org.example.expert.interfaces.external.dto.request.SigninRequestDto;
import org.example.expert.interfaces.external.dto.request.SignupRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthManager authManager;
    private final ValidateManager validateManager;

    @PostMapping("/auth/signup")
    public ResponseEntity<String> signup(
            @Valid @RequestBody final SignupRequestDto dto
    ) {
        validateManager.validateSignup(dto.email(), dto.password(), dto.userRole());

        String token = authManager.handleSignup(dto);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/auth/signin")
    public ResponseEntity<String> signin(
            @Valid @RequestBody final SigninRequestDto dto
    ) {
        validateManager.validateSignin(dto.email(), dto.password());

        String token = authManager.handleSignin(dto);
        return ResponseEntity.ok(token);
    }
}
