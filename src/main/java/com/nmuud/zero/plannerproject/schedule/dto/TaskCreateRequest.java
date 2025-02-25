package com.nmuud.zero.plannerproject.schedule.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskCreateRequest {
    private final String title;
    private final String description;
    private final LocalDateTime taskAt;
}
