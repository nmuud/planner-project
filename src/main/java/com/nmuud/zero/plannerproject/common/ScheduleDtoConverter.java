package com.nmuud.zero.plannerproject.common;

import com.nmuud.zero.plannerproject.schedule.dto.ScheduleDto;
import com.nmuud.zero.plannerproject.schedule.entity.Schedule;
import com.nmuud.zero.plannerproject.schedule.dto.EventDto;
import com.nmuud.zero.plannerproject.schedule.dto.NotificationDto;
import com.nmuud.zero.plannerproject.schedule.dto.TaskDto;

public abstract class ScheduleDtoConverter {

    public static ScheduleDto fromSchedule(Schedule schedule) {
        switch (schedule.getScheduleType()) {
            case EVENT:
                return EventDto.builder()
                        .scheduleId(schedule.getId())
                        .startAt(schedule.getStartAt())
                        .endAt(schedule.getEndAt())
                        .title(schedule.getTitle())
                        .description(schedule.getDescription())
                        .writerId(schedule.getWriter().getId())
                        .build();
            case TASK:
                return TaskDto.builder()
                        .scheduleId(schedule.getId())
                        .taskAt(schedule.getStartAt())
                        .title(schedule.getTitle())
                        .description(schedule.getDescription())
                        .writerId(schedule.getWriter().getId())
                        .build();
            case NOTIFICATION:
                return NotificationDto.builder()
                        .scheduleId(schedule.getId())
                        .notifyAt(schedule.getStartAt())
                        .title(schedule.getTitle())
                        .writerId(schedule.getWriter().getId())
                        .build();
            default:
                throw new RuntimeException("Bad Request");
        }
    }
}
