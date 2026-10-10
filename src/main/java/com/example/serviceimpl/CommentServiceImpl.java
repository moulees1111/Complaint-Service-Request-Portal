package com.example.serviceimpl;

import com.example.dto.CommentRequestDto;
import com.example.exception.ResourceNotFoundException;
import com.example.dto.CommentResponseDto;
import com.example.entity.Comment;
import com.example.entity.Complaint;
import com.example.mapper.CommentMapper;
import com.example.repository.CommentRepository;
import com.example.repository.ComplaintRepository;
import com.example.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ComplaintRepository complaintRepository;

    @Override
    public CommentResponseDto addComment(
            CommentRequestDto request) {

        log.info(
                "Adding comment to complaint ID: {}",
                request.getComplaintId()
        );

        Complaint complaint =
                complaintRepository.findById(
                        request.getComplaintId()
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Complaint not found"
                        ));

        Comment comment =
                CommentMapper.toEntity(request);

        comment.setComplaint(complaint);

        Comment savedComment =
                commentRepository.save(comment);

        log.info(
                "Comment created successfully with ID: {}",
                savedComment.getId()
        );

        return CommentMapper.toResponseDto(savedComment);
    }
    @Override
    public List<CommentResponseDto> getCommentsByComplaint(
            Long complaintId) {

        log.info(
                "Fetching comments for complaint ID: {}",
                complaintId
        );

        return commentRepository
                .findByComplaintId(complaintId)
                .stream()
                .map(CommentMapper::toResponseDto)
                .toList();
    }
    @Override
    public void deleteComment(Long id) {

        log.info("Deleting comment with ID: {}", id);

        if (!commentRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    "Comment not found"
            );
        }

        commentRepository.deleteById(id);

        log.info(
                "Comment deleted successfully with ID: {}",
                id
        );
    }
}