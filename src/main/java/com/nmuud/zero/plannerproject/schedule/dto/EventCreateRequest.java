package com.nmuud.zero.plannerproject.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class EventCreateRequest {
    @NotBlank
    private final String title;
    private final String description;
    @NotNull
    private final LocalDateTime startAt;
    @NotNull
    private final LocalDateTime endAt;
    private final List<Long> attendeeIds = new ArrayList<>();
}
