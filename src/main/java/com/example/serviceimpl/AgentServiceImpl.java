package com.example.serviceimpl;

import com.example.dto.AgentRequestDto;
import com.example.exception.DuplicateResourceException;
import com.example.exception.ResourceNotFoundException;
import com.example.dto.AgentResponseDto;
import com.example.entity.Agent;
import com.example.mapper.AgentMapper;
import com.example.repository.AgentRepository;
import com.example.service.AgentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class AgentServiceImpl implements AgentService {

    private final AgentRepository agentRepository;

    @Override
    public AgentResponseDto createAgent(
            AgentRequestDto request) {

        log.info(
                "Creating agent with email: {}",
                request.getEmail()
        );

        if (agentRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException(
                    "Agent email already exists"
            );
        }

        Agent agent =
                AgentMapper.toEntity(request);

        Agent savedAgent =
                agentRepository.save(agent);

        log.info(
                "Agent created successfully with ID: {}",
                savedAgent.getId()
        );

        return AgentMapper.toResponseDto(savedAgent);
    }

    @Override
    public AgentResponseDto getAgentById(Long id) {

        log.info("Fetching agent with ID: {}", id);

        Agent agent =
                agentRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Agent not found"
                                ));

        return AgentMapper.toResponseDto(agent);
    }
    @Override
    public List<AgentResponseDto> getAllAgents() {

        log.info("Fetching all agents");

        return agentRepository.findAll()
                .stream()
                .map(AgentMapper::toResponseDto)
                .toList();
    }
    @Override
    public AgentResponseDto updateAgent(
            Long id,
            AgentRequestDto request) {

        log.info("Updating agent with ID: {}", id);

        Agent agent =
                agentRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Agent not found"
                                ));

        agent.setName(request.getName());
        agent.setEmail(request.getEmail());
        agent.setDepartment(request.getDepartment());

        Agent updatedAgent =
                agentRepository.save(agent);

        log.info(
                "Agent updated successfully with ID: {}",
                id
        );

        return AgentMapper.toResponseDto(updatedAgent);
    }
    @Override
    public void deleteAgent(Long id) {

        log.info("Deleting agent with ID: {}", id);

        if (!agentRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    "Agent not found"
            );
        }

        agentRepository.deleteById(id);

        log.info(
                "Agent deleted successfully with ID: {}",
                id
        );
    }
}