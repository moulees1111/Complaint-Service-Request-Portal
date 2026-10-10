package com.example.service;

import com.example.dto.ComplaintRequestDto;
import com.example.dto.ComplaintResponseDto;
import com.example.entity.ComplaintPriority;
import com.example.entity.ComplaintStatus;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ComplaintService {

    ComplaintResponseDto createComplaint(
            ComplaintRequestDto request
    );

    ComplaintResponseDto getComplaintById(Long id);

    List<ComplaintResponseDto> getAllComplaints();

    ComplaintResponseDto updateComplaint(
            Long id,
            ComplaintRequestDto request
    );

    void deleteComplaint(Long id);

    List<ComplaintResponseDto> getComplaintsByStatus(
            ComplaintStatus status
    );

    List<ComplaintResponseDto> getComplaintsByPriority(
            ComplaintPriority priority
    );

    List<ComplaintResponseDto> searchComplaints(
            String keyword
    );
    
    List<ComplaintResponseDto> getComplaintsByPriorityAndStatus(
            ComplaintPriority priority,
            ComplaintStatus status
    );
    
    List<ComplaintResponseDto> getLatestComplaintsByStatus(
            ComplaintStatus status
    );
    
    ComplaintResponseDto assignAgent(
            Long complaintId,
            Long agentId
    );

    ComplaintResponseDto updateStatus(
            Long complaintId,
            ComplaintStatus status
    );

    Page<ComplaintResponseDto> getComplaintsWithPagination(
            int page,
            int size,
            String sortBy,
            String direction
    );
}