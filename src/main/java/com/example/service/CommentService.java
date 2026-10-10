package com.example.service;

import com.example.dto.CommentRequestDto;
import com.example.dto.CommentResponseDto;

import java.util.List;

public interface CommentService {

    CommentResponseDto addComment(CommentRequestDto request);

    List<CommentResponseDto> getCommentsByComplaint(
            Long complaintId
    );

    void deleteComment(Long id);
}