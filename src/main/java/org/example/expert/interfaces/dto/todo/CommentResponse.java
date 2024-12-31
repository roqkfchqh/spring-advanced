package org.example.expert.interfaces.dto.todo;

import lombok.Getter;
import org.example.expert.interfaces.dto.user.UserResponse;

@Getter
public class CommentResponse {

    private final Long id;
    private final String contents;
    private final UserResponse user;

    public CommentResponse(Long id, String contents, UserResponse user) {
        this.id = id;
        this.contents = contents;
        this.user = user;
    }
}