package org.example.expert.application.mapper;

import org.example.expert.domain.todo.Comment;
import org.example.expert.application.dto.response.CommentResponseDto;
import org.example.expert.application.dto.response.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper implements Mapper<Comment, CommentResponseDto> {

    @Override
    public CommentResponseDto toDto(Comment entity) {
        return new CommentResponseDto(
                entity.getId(),
                entity.getContents(),
                new UserResponse(entity.getUser().getId(), entity.getUser().getEmail())
        );
    }
}