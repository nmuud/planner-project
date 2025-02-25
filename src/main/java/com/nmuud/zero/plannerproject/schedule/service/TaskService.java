package com.nmuud.zero.plannerproject.schedule.service;


import com.nmuud.zero.plannerproject.common.AuthUser;
import com.nmuud.zero.plannerproject.schedule.dto.TaskCreateRequest;
import com.nmuud.zero.plannerproject.schedule.entity.Schedule;
import com.nmuud.zero.plannerproject.schedule.repository.ScheduleRepository;
import com.nmuud.zero.plannerproject.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final UserService userService;
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public void create(TaskCreateRequest taskCreateRequest, AuthUser authUser) {
        final Schedule taskSchedule = Schedule.task(
                taskCreateRequest.getTitle(),
                taskCreateRequest.getDescription(),
                taskCreateRequest.getTaskAt(),
                userService.findByUserId(authUser.getId()));
        scheduleRepository.save(taskSchedule);
    }
}
