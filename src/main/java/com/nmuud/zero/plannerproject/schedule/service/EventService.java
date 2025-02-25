package com.nmuud.zero.plannerproject.schedule.service;

import com.nmuud.zero.plannerproject.schedule.dto.EventCreateRequest;
import com.nmuud.zero.plannerproject.schedule.entity.Schedule;
import com.nmuud.zero.plannerproject.schedule.repository.ScheduleRepository;
import com.nmuud.zero.plannerproject.common.AuthUser;
import com.nmuud.zero.plannerproject.schedule.entity.Engagement;
import com.nmuud.zero.plannerproject.schedule.repository.EngagementRepository;
import com.nmuud.zero.plannerproject.common.enums.RequestStatus;
import com.nmuud.zero.plannerproject.user.entity.User;
import com.nmuud.zero.plannerproject.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final UserService userService;
    private final ScheduleRepository scheduleRepository;
    private final EngagementRepository engagementRepository;

    @Transactional
    public void create(EventCreateRequest eventCreateRequest, AuthUser authUser) {

        final List<Engagement> engagementList =
                engagementRepository.findAll();
        if (engagementList.stream()
                .anyMatch(e -> eventCreateRequest.getAttendeeIds().contains(e.getAttendee().getId())
                        && e.getRequestStatus() == RequestStatus.ACCEPTED
                        && e.getEvent().isOverlapped(
                        eventCreateRequest.getStartAt(),
                        eventCreateRequest.getEndAt()))
        ) {
            throw new RuntimeException("cannot make engagement. period overlapped.");
        }

        final Schedule eventSchedule = Schedule.event(
                eventCreateRequest.getTitle(),
                eventCreateRequest.getDescription(),
                eventCreateRequest.getStartAt(),
                eventCreateRequest.getEndAt(),
                userService.findByUserId(authUser.getId())
        );

        scheduleRepository.save(eventSchedule);
        eventCreateRequest.getAttendeeIds().forEach(atId -> {
            final User attendee = userService.findByUserId(atId);
            final Engagement engagement = Engagement.builder()
                    .schedule(eventSchedule)
                    .requestStatus(RequestStatus.REQUESTED)
                    .attendee(attendee)
                    .build();
            engagementRepository.save(engagement);
        });
    }
}
