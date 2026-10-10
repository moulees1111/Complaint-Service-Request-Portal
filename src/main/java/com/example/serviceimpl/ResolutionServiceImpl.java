package com.example.serviceimpl;

import com.example.dto.ResolutionRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import com.example.exception.DuplicateResourceException;
import com.example.exception.ResourceNotFoundException;
import com.example.dto.ResolutionResponseDto;
import com.example.entity.Complaint;
import com.example.entity.ComplaintStatus;
import com.example.entity.Resolution;
import com.example.mapper.ResolutionMapper;
import com.example.repository.ComplaintRepository;
import com.example.repository.ResolutionRepository;
import com.example.service.ResolutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResolutionServiceImpl implements ResolutionService {

    private final ResolutionRepository resolutionRepository;
    private final ComplaintRepository complaintRepository;

    @Override
    @Transactional
    public ResolutionResponseDto createResolution(
            ResolutionRequestDto request) {

        log.info(
                "Creating resolution for complaint {}",
                request.getComplaintId()
        );

        Complaint complaint = complaintRepository
                .findById(request.getComplaintId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Complaint not found"));

        if (resolutionRepository
                .existsByComplaintId(request.getComplaintId())) {

            throw new DuplicateResourceException(
                    "Resolution already exists for this complaint");
        }

        Resolution resolution =
                ResolutionMapper.toEntity(request);

        resolution.setComplaint(complaint);

        Resolution savedResolution =
                resolutionRepository.save(resolution);

        // Update complaint status
        complaint.setStatus(ComplaintStatus.RESOLVED);

        complaintRepository.save(complaint);

        log.info(
                "Resolution created and complaint {} marked as RESOLVED",
                request.getComplaintId()
        );

        return ResolutionMapper.toResponseDto(savedResolution);
    }
    @Override
    @Transactional
    public ResolutionResponseDto getResolutionByComplaint(
            Long complaintId) {

        Resolution resolution =
                resolutionRepository.findByComplaintId(complaintId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Resolution not found"));

        return ResolutionMapper.toResponseDto(resolution);
    }
}