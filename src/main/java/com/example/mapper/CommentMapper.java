package com.example.mapper;

import com.example.dto.CommentRequestDto;
import com.example.dto.CommentResponseDto;
import com.example.entity.Comment;

public class CommentMapper {

    public static Comment toEntity(CommentRequestDto dto) {

        Comment comment = new Comment();

        comment.setMessage(dto.getMessage());

        return comment;
    }

    public static CommentResponseDto toResponseDto(Comment comment) {

        return new CommentResponseDto(
                comment.getId(),
                comment.getMessage(),
                comment.getComplaint().getId()
        );
    }
}