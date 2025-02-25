package com.nmuud.zero.plannerproject.schedule.service;

import com.nmuud.zero.plannerproject.common.AuthUser;
import com.nmuud.zero.plannerproject.common.ScheduleDtoConverter;
import com.nmuud.zero.plannerproject.common.util.TimeRange;
import com.nmuud.zero.plannerproject.schedule.dto.ScheduleDto;
import com.nmuud.zero.plannerproject.schedule.repository.EngagementRepository;
import com.nmuud.zero.plannerproject.schedule.repository.ScheduleRepository;
import com.nmuud.zero.plannerproject.share.dto.SharedScheduleDto;
import com.nmuud.zero.plannerproject.share.service.ShareService;
import com.nmuud.zero.plannerproject.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ScheduleReadService {

    private final ScheduleRepository scheduleRepository;
    private final EngagementRepository engagementRepository;

    private final ShareService shareService;
    private final UserService userService;

    public List<ScheduleDto> findSchedulesByDay(AuthUser authUser, LocalDate date) {
        return findSchedulesWithinTimeRange(authUser, TimeRange.of(date, date));
    }

    public List<ScheduleDto> findSchedulesByWeek(AuthUser authUser, LocalDate startOfWeek) {
        return findSchedulesWithinTimeRange(authUser, TimeRange.of(startOfWeek, startOfWeek.plusDays(6)));
    }

    public List<ScheduleDto> findSchedulesByMonth(AuthUser authUser, YearMonth yearMonth) {
        return findSchedulesWithinTimeRange(authUser, TimeRange.of(yearMonth.atDay(1), yearMonth.atEndOfMonth()));
    }


    public List<SharedScheduleDto> findSharedSchedulesByDay(LocalDate date, AuthUser authUser) {
        return Stream.concat(shareService.findSharedUserIdsByUser(authUser).stream(), Stream.of(authUser.getId()))
                .map(userId -> SharedScheduleDto.builder()
                        .userId(userId)
                        .name(userService.findByUserId(userId).getName())
                        .me(userId.equals(authUser.getId()))
                        .schedules(findSchedulesByDay(AuthUser.of(userId), date))
                        .build())
                .collect(Collectors.toList());

    }

    public List<SharedScheduleDto> findSharedSchedulesByWeek(LocalDate startOfWeek, AuthUser authUser) {
        return Stream.concat(shareService.findSharedUserIdsByUser(authUser).stream(), Stream.of(authUser.getId()))
                .map(userId -> SharedScheduleDto.builder()
                        .userId(userId)
                        .name(userService.findByUserId(userId).getName())
                        .me(userId.equals(authUser.getId()))
                        .schedules(findSchedulesByWeek(AuthUser.of(userId), startOfWeek))
                        .build())
                .collect(Collectors.toList());
    }

    public List<SharedScheduleDto> findSharedSchedulesByMonth(YearMonth yearMonth, AuthUser authUser) {
        return Stream.concat(shareService.findSharedUserIdsByUser(authUser).stream(), Stream.of(authUser.getId()))
                .map(userId -> SharedScheduleDto.builder()
                        .userId(userId)
                        .name(userService.findByUserId(userId).getName())
                        .me(userId.equals(authUser.getId()))
                        .schedules(findSchedulesByMonth(AuthUser.of(userId), yearMonth))
                        .build())
                .collect(Collectors.toList());
    }

    private List<ScheduleDto> findSchedulesWithinTimeRange(AuthUser authUser, TimeRange timeRange) {
        return Stream.concat(
                scheduleRepository
                        .findAllByWriter_Id(authUser.getId())
                        .stream()
                        .filter(schedule -> schedule.getStartAt() != null) // 일정 없어도 에러 안뜸
                        .filter(schedule -> schedule.isOverlapped(timeRange))
                        .map(schedule -> ScheduleDtoConverter.fromSchedule(schedule)),
                engagementRepository
                        .findAllByAttendee_Id(authUser.getId())
                        .stream()
                        .filter(engagement -> engagement.getSchedule().getStartAt() != null) // 일정 없어도 에러 안뜸
                        .filter(engagement -> engagement.isOverlapped(timeRange))
                        .map(engagement -> ScheduleDtoConverter.fromSchedule(engagement.getSchedule()))
        ).collect(Collectors.toList());
    }
}
