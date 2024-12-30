package org.example.expert.domain.user.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.expert.domain.user.Email;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SigninRequest {
    
    @NotBlank
    private Email email;
    @NotBlank
    private String password;
}
