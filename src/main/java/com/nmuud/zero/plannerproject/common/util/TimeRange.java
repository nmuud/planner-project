package com.nmuud.zero.plannerproject.common.util;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
public class TimeRange {

    private final LocalDateTime startAt;
    private final LocalDateTime endAt;

    private TimeRange(LocalDateTime startAt, LocalDateTime endAt) {
        this.startAt = startAt;
        if (endAt == null) {
            this.endAt = startAt;
        } else {
            this.endAt = endAt;
        }

    }

    public static TimeRange of(LocalDateTime startAt, LocalDateTime endAt) {
        return new TimeRange(startAt, endAt);
    }

    public static TimeRange of(LocalDate startDate, LocalDate endDate) {
        return new TimeRange(startDate.atStartOfDay(), LocalDateTime.of(endDate, LocalTime.MAX));

    }

    public boolean isOverlapped(LocalDateTime startAt, LocalDateTime endAt) {
        return this.startAt.isBefore(endAt) && startAt.isBefore(this.endAt);
    }

    public boolean isOverlapped(TimeRange timeRange) {
        return isOverlapped(timeRange.startAt, timeRange.endAt);
    }
}
