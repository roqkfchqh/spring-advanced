package org.example.expert.interfaces.external.mapper;

import org.example.expert.domain.todo.comment.Comment;
import org.example.expert.interfaces.external.dto.response.CommentResponse;
import org.example.expert.interfaces.external.dto.response.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper implements Mapper<Comment, CommentResponse> {

    @Override
    public CommentResponse toDto(Comment entity) {
        return new CommentResponse(
                entity.getId(),
                entity.getContents(),
                new UserResponse(entity.getUser().getId(), entity.getUser().getEmail())
        );
    }
}