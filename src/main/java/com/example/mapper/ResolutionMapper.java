package com.example.mapper;

import com.example.dto.ResolutionRequestDto;
import com.example.dto.ResolutionResponseDto;
import com.example.entity.Resolution;

public class ResolutionMapper {

    public static Resolution toEntity(ResolutionRequestDto dto) {

        Resolution resolution = new Resolution();

        resolution.setResolutionText(dto.getResolutionText());
        resolution.setResolvedBy(dto.getResolvedBy());

        return resolution;
    }

    public static ResolutionResponseDto toResponseDto(Resolution resolution) {

        return new ResolutionResponseDto(
                resolution.getId(),
                resolution.getResolutionText(),
                resolution.getResolvedBy(),
                resolution.getComplaint().getId()
        );
    }
}