package org.example.expert.interfaces.external.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@RequiredArgsConstructor
public class UserResponse {

    private final Long id;
    private final String email;
    private final String bearerToken;

    //사용자 정보 생성자
    public UserResponse(Long id, String email) {
        this.id = id;
        this.email = email;
        this.bearerToken = null;
    }

    //저장 응답 생성자
    public UserResponse(String bearerToken) {
        this.id = null;
        this.email = null;
        this.bearerToken = bearerToken;
    }
}
