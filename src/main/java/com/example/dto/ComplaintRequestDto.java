package com.example.dto;

import com.example.entity.ComplaintPriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ComplaintRequestDto {

    @NotBlank(message = "Complaint title is required")
    private String title;

    @NotBlank(message = "Complaint description is required")
    @Size(min = 10, max = 1000,
          message = "Description must be between 10 and 1000 characters")
    private String description;

    @NotNull(message = "Priority is required")
    private ComplaintPriority priority;

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Category ID is required")
    private Long categoryId;

    private Long agentId;
}