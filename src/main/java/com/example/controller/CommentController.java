package com.example.controller;

import com.example.dto.CommentRequestDto;
import com.example.dto.CommentResponseDto;
import com.example.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponseDto> addComment(
            @Valid @RequestBody CommentRequestDto request) {

        return ResponseEntity.ok(
                commentService.addComment(request)
        );
    }

    @GetMapping("/complaint/{complaintId}")
    public ResponseEntity<List<CommentResponseDto>> getCommentsByComplaint(
            @PathVariable Long complaintId) {

        return ResponseEntity.ok(
                commentService.getCommentsByComplaint(complaintId)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long id) {

        commentService.deleteComment(id);

        return ResponseEntity.noContent().build();
    }
}