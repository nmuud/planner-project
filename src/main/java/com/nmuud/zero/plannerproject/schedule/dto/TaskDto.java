package com.nmuud.zero.plannerproject.schedule.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class TaskDto implements ScheduleDto {

    private final Long scheduleId;
    private final Long writerId;
    private final String title;
    private final String description;
    private final LocalDateTime taskAt;


    @Override
    public ScheduleType getScheduleType() {
        return ScheduleType.TASK;
    }
}
