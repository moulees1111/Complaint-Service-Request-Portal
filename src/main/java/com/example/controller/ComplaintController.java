package com.example.controller;

import com.example.dto.ComplaintRequestDto;
import com.example.dto.ComplaintResponseDto;
import com.example.entity.ComplaintPriority;
import com.example.entity.ComplaintStatus;
import com.example.service.ComplaintService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/complaints")
@RequiredArgsConstructor
public class ComplaintController {

    private final ComplaintService complaintService;

    // Create complaint
    @PostMapping
    public ResponseEntity<ComplaintResponseDto> createComplaint(
            @Valid @RequestBody ComplaintRequestDto request) {

        return ResponseEntity.ok(
                complaintService.createComplaint(request)
        );
    }
    @GetMapping("/latest")
    public ResponseEntity<List<ComplaintResponseDto>>
            getLatestComplaintsByStatus(
                    @RequestParam ComplaintStatus status) {

        return ResponseEntity.ok(
                complaintService.getLatestComplaintsByStatus(status)
        );
    }
    // Get complaint by ID
    @GetMapping("/{id}")
    public ResponseEntity<ComplaintResponseDto> getComplaintById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                complaintService.getComplaintById(id)
        );
    }

    // Get all complaints
    @GetMapping
    public ResponseEntity<List<ComplaintResponseDto>> getAllComplaints() {

        return ResponseEntity.ok(
                complaintService.getAllComplaints()
        );
    }
    
    @GetMapping("/filter")
    public ResponseEntity<List<ComplaintResponseDto>>
            getByPriorityAndStatus(
                    @RequestParam ComplaintPriority priority,
                    @RequestParam ComplaintStatus status) {

        return ResponseEntity.ok(
                complaintService.getComplaintsByPriorityAndStatus(
                        priority, status
                )
        );
    }
    
    // Update complaint
    @PutMapping("/{id}")
    public ResponseEntity<ComplaintResponseDto> updateComplaint(
            @PathVariable Long id,
            @Valid @RequestBody ComplaintRequestDto request) {

        return ResponseEntity.ok(
                complaintService.updateComplaint(id, request)
        );
    }

    // Delete complaint
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComplaint(
            @PathVariable Long id) {

        complaintService.deleteComplaint(id);

        return ResponseEntity.noContent().build();
    }

    // Get complaints by status
    @GetMapping("/status")
    public ResponseEntity<List<ComplaintResponseDto>> getByStatus(
            @RequestParam ComplaintStatus status) {

        return ResponseEntity.ok(
                complaintService.getComplaintsByStatus(status)
        );
    }

    // Get complaints by priority
    @GetMapping("/priority")
    public ResponseEntity<List<ComplaintResponseDto>> getByPriority(
            @RequestParam ComplaintPriority priority) {

        return ResponseEntity.ok(
                complaintService.getComplaintsByPriority(priority)
        );
    }

    // Search complaints
    @GetMapping("/search")
    public ResponseEntity<List<ComplaintResponseDto>> searchComplaints(
            @RequestParam String keyword) {

        return ResponseEntity.ok(
                complaintService.searchComplaints(keyword)
        );
    }

    // Assign agent
    @PatchMapping("/{complaintId}/assign/{agentId}")
    public ResponseEntity<ComplaintResponseDto> assignAgent(
            @PathVariable Long complaintId,
            @PathVariable Long agentId) {

        return ResponseEntity.ok(
                complaintService.assignAgent(
                        complaintId,
                        agentId
                )
        );
    }

    // Update complaint status
    @PatchMapping("/{complaintId}/status")
    public ResponseEntity<ComplaintResponseDto> updateStatus(
            @PathVariable Long complaintId,
            @RequestParam ComplaintStatus status) {

        return ResponseEntity.ok(
                complaintService.updateStatus(
                        complaintId,
                        status
                )
        );
    }

    // Pagination and sorting
    @GetMapping("/page")
    public ResponseEntity<Page<ComplaintResponseDto>> getComplaintsWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        return ResponseEntity.ok(
                complaintService.getComplaintsWithPagination(
                        page,
                        size,
                        sortBy,
                        direction
                )
        );
    }
}