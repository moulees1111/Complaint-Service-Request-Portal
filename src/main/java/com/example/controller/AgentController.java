package com.example.controller;

import com.example.dto.AgentRequestDto;
import com.example.dto.AgentResponseDto;
import com.example.service.AgentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agents")
@RequiredArgsConstructor
public class AgentController {

    private final AgentService agentService;

    @PostMapping
    public ResponseEntity<AgentResponseDto> createAgent(
            @Valid @RequestBody AgentRequestDto request) {

        return ResponseEntity.ok(
                agentService.createAgent(request)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgentResponseDto> getAgentById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                agentService.getAgentById(id)
        );
    }

    @GetMapping
    public ResponseEntity<List<AgentResponseDto>> getAllAgents() {

        return ResponseEntity.ok(
                agentService.getAllAgents()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<AgentResponseDto> updateAgent(
            @PathVariable Long id,
            @Valid @RequestBody AgentRequestDto request) {

        return ResponseEntity.ok(
                agentService.updateAgent(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAgent(
            @PathVariable Long id) {

        agentService.deleteAgent(id);

        return ResponseEntity.noContent().build();
    }
}