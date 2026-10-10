package com.example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResolutionRequestDto {

    @NotBlank(message = "Resolution text is required")
    @Size(min = 5, max = 1000,
          message = "Resolution must be between 5 and 1000 characters")
    private String resolutionText;

    @NotBlank(message = "Resolved by is required")
    private String resolvedBy;

    @NotNull(message = "Complaint ID is required")
    private Long complaintId;
}