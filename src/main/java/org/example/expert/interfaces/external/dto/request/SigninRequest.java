package org.example.expert.interfaces.external.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SigninRequest {
    
    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
