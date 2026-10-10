package com.example.serviceimpl;
import com.example.dto.ComplaintRequestDto;

import com.example.exception.ResourceNotFoundException;
import com.example.dto.ComplaintResponseDto;
import com.example.entity.*;
import com.example.mapper.ComplaintMapper;
import com.example.repository.*;
import com.example.service.ComplaintService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class ComplaintServiceImpl implements ComplaintService {

    private final ComplaintRepository complaintRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final AgentRepository agentRepository;
    @Override
    @Transactional(readOnly = true)
    public List<ComplaintResponseDto> getComplaintsByPriorityAndStatus(
            ComplaintPriority priority,
            ComplaintStatus status) {

        return complaintRepository
                .findByPriorityAndStatus(priority, status)
                .stream()
                .map(ComplaintMapper::toResponseDto)
                .toList();
    }
    @Override
    @Transactional(readOnly = true)
    public List<ComplaintResponseDto> getLatestComplaintsByStatus(
            ComplaintStatus status) {

        return complaintRepository
                .findLatestComplaintsByStatus(status.name())
                .stream()
                .map(ComplaintMapper::toResponseDto)
                .toList();
    }
    
    @Override
    @Transactional
    public ComplaintResponseDto createComplaint(
            ComplaintRequestDto request) {

        log.info("Creating new complaint with title: {}",
                request.getTitle());

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Category category = categoryRepository.findById(
                request.getCategoryId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category not found"));

        Complaint complaint = ComplaintMapper.toEntity(request);

        complaint.setUser(user);
        complaint.setCategory(category);
        complaint.setStatus(ComplaintStatus.OPEN);

        if (request.getAgentId() != null) {

            Agent agent = agentRepository.findById(
                    request.getAgentId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Agent not found"));

            complaint.setAgent(agent);
            complaint.setStatus(ComplaintStatus.ASSIGNED);
        }

        Complaint savedComplaint =
                complaintRepository.save(complaint);

        log.info("Complaint created successfully with ID: {}",
                savedComplaint.getId());

        return ComplaintMapper.toResponseDto(savedComplaint);
    }
    @Override
    @Transactional
    public ComplaintResponseDto getComplaintById(Long id) {

        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Complaint not found with id: " + id
                        )
                );

        return ComplaintMapper.toResponseDto(complaint);
    }
    @Override
    @Transactional
    public List<ComplaintResponseDto> getAllComplaints() {

        return complaintRepository.findAll()
                .stream()
                .map(ComplaintMapper::toResponseDto)
                .toList();
    }
    @Override
    @Transactional
    public ComplaintResponseDto updateComplaint(
            Long id,
            ComplaintRequestDto request) {

        Complaint complaint =
                complaintRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Complaint not found with id: "
                                                + id
                                )
                        );

        Category category =
                categoryRepository.findById(request.getCategoryId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Category not found with id: "
                                                + request.getCategoryId()
                                )
                        );

        complaint.setTitle(request.getTitle());
        complaint.setDescription(request.getDescription());
        complaint.setPriority(request.getPriority());
        complaint.setCategory(category);

        Complaint updatedComplaint =
                complaintRepository.save(complaint);

        return ComplaintMapper.toResponseDto(updatedComplaint);
    }
    @Override
    @Transactional
    public void deleteComplaint(Long id) {

        if (!complaintRepository.existsById(id)) {

            throw new ResourceNotFoundException(
                    "Complaint not found with id: " + id
            );
        }

        complaintRepository.deleteById(id);
    }
    @Override
    @Transactional
    public List<ComplaintResponseDto> searchComplaints(
            String keyword) {

        return complaintRepository
                .searchComplaints(keyword)
                .stream()
                .map(ComplaintMapper::toResponseDto)
                .toList();
    }
    @Override
    @Transactional
    public List<ComplaintResponseDto> getComplaintsByStatus(
            ComplaintStatus status) {

        return complaintRepository
                .findByStatus(status)
                .stream()
                .map(ComplaintMapper::toResponseDto)
                .toList();
    }
    @Override
    @Transactional
    public List<ComplaintResponseDto> getComplaintsByPriority(
            ComplaintPriority priority) {

        return complaintRepository
                .findByPriority(priority)
                .stream()
                .map(ComplaintMapper::toResponseDto)
                .toList();
    }
    @Override
    @Transactional
    public ComplaintResponseDto assignAgent(
            Long complaintId,
            Long agentId) {

        log.info(
                "Assigning agent {} to complaint {}",
                agentId,
                complaintId
        );

        Complaint complaint = complaintRepository
                .findById(complaintId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Complaint not found"));

        Agent agent = agentRepository
                .findById(agentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Agent not found"));

        complaint.setAgent(agent);

        if (complaint.getStatus() == ComplaintStatus.OPEN) {
            complaint.setStatus(ComplaintStatus.ASSIGNED);
        }

        Complaint updatedComplaint =
                complaintRepository.save(complaint);

        log.info(
                "Agent {} assigned successfully to complaint {}",
                agentId,
                complaintId
        );

        return ComplaintMapper.toResponseDto(updatedComplaint);
    }
    @Override
    @Transactional
    public ComplaintResponseDto updateStatus(
            Long complaintId,
            ComplaintStatus status) {

        log.info(
                "Updating complaint {} status to {}",
                complaintId,
                status
        );

        Complaint complaint = complaintRepository
                .findById(complaintId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Complaint not found"));

        complaint.setStatus(status);

        Complaint updatedComplaint =
                complaintRepository.save(complaint);

        log.info(
                "Complaint {} status updated successfully",
                complaintId
        );

        return ComplaintMapper.toResponseDto(updatedComplaint);
    }
    @Override
    @Transactional
    public Page<ComplaintResponseDto> getComplaintsWithPagination(
            int page,
            int size,
            String sortBy,
            String direction) {

        Sort sort;

        if (direction.equalsIgnoreCase("desc")) {
            sort = Sort.by(sortBy).descending();
        } else {
            sort = Sort.by(sortBy).ascending();
        }

        Pageable pageable =
                PageRequest.of(page, size, sort);

        return complaintRepository
                .findAll(pageable)
                .map(ComplaintMapper::toResponseDto);
    }
    
}