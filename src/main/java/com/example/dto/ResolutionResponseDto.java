package com.example.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResolutionResponseDto {

    private Long id;
    private String resolutionText;
    private String resolvedBy;
    private Long complaintId;
}