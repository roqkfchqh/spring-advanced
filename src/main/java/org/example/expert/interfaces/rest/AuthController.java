package org.example.expert.interfaces.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.expert.application.manager.AuthManager;
import org.example.expert.interfaces.external.dto.request.SigninDto;
import org.example.expert.interfaces.external.dto.request.SignupDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthManager authManager;

    @PostMapping("/auth/signup")
    public ResponseEntity<String> signup(
            @Valid @RequestBody final SignupDto dto
    ) {
        String token = authManager.handleSignup(dto);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/auth/signin")
    public ResponseEntity<String> signin(
            @Valid @RequestBody final SigninDto dto
    ) {
        String token = authManager.handleSignin(dto);
        return ResponseEntity.ok(token);
    }
}
