package com.example.mapper;

import com.example.dto.AgentRequestDto;
import com.example.dto.AgentResponseDto;
import com.example.entity.Agent;

public class AgentMapper {

    public static Agent toEntity(AgentRequestDto dto) {

        Agent agent = new Agent();

        agent.setName(dto.getName());
        agent.setEmail(dto.getEmail());
        agent.setDepartment(dto.getDepartment());

        return agent;
    }

    public static AgentResponseDto toResponseDto(Agent agent) {

        return new AgentResponseDto(
                agent.getId(),
                agent.getName(),
                agent.getEmail(),
                agent.getDepartment()
        );
    }
}