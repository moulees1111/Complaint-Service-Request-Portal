package com.example.service;

import com.example.dto.ResolutionRequestDto;
import com.example.dto.ResolutionResponseDto;

public interface ResolutionService {

    ResolutionResponseDto createResolution(
            ResolutionRequestDto request
    );

    ResolutionResponseDto getResolutionByComplaint(
            Long complaintId
    );
}