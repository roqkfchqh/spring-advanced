package org.example.expert.application.dto.response;

import java.time.LocalDateTime;

public record TodoResponse(Long id, String title, String contents, String weather, UserResponse user,
                           LocalDateTime createdAt, LocalDateTime modifiedAt) {

}
