package com.example.service;

import com.example.dto.AgentRequestDto;
import com.example.dto.AgentResponseDto;

import java.util.List;

public interface AgentService {

    AgentResponseDto createAgent(AgentRequestDto request);

    AgentResponseDto getAgentById(Long id);

    List<AgentResponseDto> getAllAgents();

    AgentResponseDto updateAgent(
            Long id,
            AgentRequestDto request
    );

    void deleteAgent(Long id);
}