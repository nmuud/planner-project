package com.nmuud.zero.plannerproject.schedule.controller;

import com.nmuud.zero.plannerproject.common.AuthUser;
import com.nmuud.zero.plannerproject.schedule.dto.EventCreateRequest;
import com.nmuud.zero.plannerproject.schedule.dto.NotificationCreateRequest;
import com.nmuud.zero.plannerproject.schedule.dto.ScheduleDto;
import com.nmuud.zero.plannerproject.schedule.dto.TaskCreateRequest;
import com.nmuud.zero.plannerproject.schedule.service.EventService;
import com.nmuud.zero.plannerproject.schedule.service.NotificationService;
import com.nmuud.zero.plannerproject.schedule.service.ScheduleReadService;
import com.nmuud.zero.plannerproject.schedule.service.TaskService;
import com.nmuud.zero.plannerproject.share.dto.ScheduleShareRequest;
import com.nmuud.zero.plannerproject.share.dto.ShareReplyRequest;
import com.nmuud.zero.plannerproject.share.service.ShareService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/schedules")
@RestController
public class ScheduleController {

    private final ScheduleReadService scheduleReadService;

    private final TaskService taskService;
    private final EventService eventService;
    private final NotificationService notificationService;

    private final ShareService shareService;


    @GetMapping
    public String scheduleHome() {
        return "redirect:/schedules/view";
    }

    @PostMapping("/tasks")
    public ResponseEntity<Void> createTask(
            @RequestBody TaskCreateRequest taskCreateRequest,
            AuthUser authUser) {

        taskService.create(taskCreateRequest, authUser);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/events")
    public ResponseEntity<Void> createEvent(
            @Valid @RequestBody EventCreateRequest eventCreateRequest,
            AuthUser authUser) {
        eventService.create(eventCreateRequest, authUser);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/notifications")
    public ResponseEntity<Void> createNotification(
            @RequestBody NotificationCreateRequest notificationCreateRequest,
            AuthUser authUser) {
        notificationService.create(notificationCreateRequest, authUser);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/day")
    public List<ScheduleDto> findSchedulesByDay(
            AuthUser authUser,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        return scheduleReadService.findSchedulesByDay(authUser, date == null ? LocalDate.now() : date);
    }

    @GetMapping("/week")
    public List<ScheduleDto> findSchedulesByWeek(
            AuthUser authUser,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startOfWeek
    ) {
        return scheduleReadService.findSchedulesByWeek(authUser, startOfWeek == null ? LocalDate.now() : startOfWeek);
    }

    @GetMapping("/month")
    public List<ScheduleDto> findSchedulesByMonth(
            AuthUser authUser,
            @RequestParam(required = false)
            @DateTimeFormat(pattern = "yyyy-MM") String yearMonth
    ) {
        return scheduleReadService.findSchedulesByMonth(authUser, yearMonth == null ? YearMonth.now() : YearMonth.parse(yearMonth));
    }


    @PostMapping("/shares")
    public void shareSchedule(
            AuthUser authUser, @RequestBody ScheduleShareRequest scheduleShareRequest
    ) {
        shareService.createShare(authUser.getId(),
                scheduleShareRequest.getToUserId(),
                scheduleShareRequest.getDirection());
    }

    @PutMapping("/shares/{shareId}")
    public void replyToShareRequest(
            @PathVariable Long shareId, @RequestBody ShareReplyRequest shareReplyRequest,
            AuthUser authUser
    ) {
        shareService.replyToShareRequest(shareId, authUser.getId(), shareReplyRequest.getType());
    }
}
