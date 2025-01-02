package org.example.expert.presentation.external.mapper;

import org.example.expert.domain.todo.comment.Comment;
import org.example.expert.presentation.external.dto.response.CommentResponseDto;
import org.example.expert.presentation.external.dto.response.UserResponse;
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