package com.example.dto;

import com.example.entity.ComplaintPriority;

import com.example.entity.ComplaintStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ComplaintResponseDto {

    private Long id;

    private String title;

    private String description;

    private ComplaintStatus status;

    private ComplaintPriority priority;

    private Long userId;

    private String userName;

    private Long categoryId;

    private String categoryName;

    private Long agentId;

    private String agentName;
}