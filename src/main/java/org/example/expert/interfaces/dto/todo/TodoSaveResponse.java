package org.example.expert.interfaces.dto.todo;

import lombok.Getter;
import org.example.expert.interfaces.dto.user.UserResponse;

@Getter
public class TodoSaveResponse {

    private final Long id;
    private final String title;
    private final String contents;
    private final String weather;
    private final UserResponse user;

    public TodoSaveResponse(Long id, String title, String contents, String weather, UserResponse user) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.weather = weather;
        this.user = user;
    }
}
