package com.example.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AgentResponseDto {

    private Long id;
    private String name;
    private String email;
    private String department;
}