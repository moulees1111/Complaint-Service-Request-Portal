package com.example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequestDto {

    @NotBlank(message = "Comment message is required")
    @Size(max = 1000, message = "Comment cannot exceed 1000 characters")
    private String message;

    @NotNull(message = "Complaint ID is required")
    private Long complaintId;
}