package com.nmuud.zero.plannerproject.share.dto;

import com.nmuud.zero.plannerproject.schedule.dto.ScheduleDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class SharedScheduleDto {

    private final Long userId;
    private final String name;
    private final Boolean me;
    private final List<ScheduleDto> schedules;
}
