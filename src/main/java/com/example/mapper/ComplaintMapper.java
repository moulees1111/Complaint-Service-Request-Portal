package com.example.mapper;

import com.example.dto.ComplaintRequestDto;
import com.example.dto.ComplaintResponseDto;
import com.example.entity.Complaint;

public class ComplaintMapper {

    public static Complaint toEntity(ComplaintRequestDto dto) {

        Complaint complaint = new Complaint();

        complaint.setTitle(dto.getTitle());
        complaint.setDescription(dto.getDescription());
        complaint.setPriority(dto.getPriority());

        return complaint;
    }

    public static ComplaintResponseDto toResponseDto(Complaint complaint) {

        Long userId = null;
        String userName = null;

        if (complaint.getUser() != null) {
            userId = complaint.getUser().getId();
            userName = complaint.getUser().getName();
        }

        Long categoryId = null;
        String categoryName = null;

        if (complaint.getCategory() != null) {
            categoryId = complaint.getCategory().getId();
            categoryName = complaint.getCategory().getName();
        }

        Long agentId = null;
        String agentName = null;

        if (complaint.getAgent() != null) {
            agentId = complaint.getAgent().getId();
            agentName = complaint.getAgent().getName();
        }

        return new ComplaintResponseDto(
                complaint.getId(),
                complaint.getTitle(),
                complaint.getDescription(),
                complaint.getStatus(),
                complaint.getPriority(),
                userId,
                userName,
                categoryId,
                categoryName,
                agentId,
                agentName
        );
    }
}