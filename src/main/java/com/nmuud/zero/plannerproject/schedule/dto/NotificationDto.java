package com.nmuud.zero.plannerproject.schedule.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class NotificationDto implements ScheduleDto {

    private final Long scheduleId;
    private final String title;
    private final LocalDateTime notifyAt;
    private final Long writerId;
    @Override
    public ScheduleType getScheduleType() {
        return ScheduleType.NOTIFICATION;
    }
}
