package com.example.controller;

import com.example.dto.ResolutionRequestDto;
import com.example.dto.ResolutionResponseDto;
import com.example.service.ResolutionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resolutions")
@RequiredArgsConstructor
public class ResolutionController {

    private final ResolutionService resolutionService;

    @PostMapping
    public ResponseEntity<ResolutionResponseDto> createResolution(
            @Valid @RequestBody ResolutionRequestDto request) {

        return ResponseEntity.ok(
                resolutionService.createResolution(request)
        );
    }

    @GetMapping("/complaint/{complaintId}")
    public ResponseEntity<ResolutionResponseDto> getResolutionByComplaint(
            @PathVariable Long complaintId) {

        return ResponseEntity.ok(
                resolutionService.getResolutionByComplaint(complaintId)
        );
    }
}