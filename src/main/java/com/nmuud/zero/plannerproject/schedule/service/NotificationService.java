package com.nmuud.zero.plannerproject.schedule.service;


import com.nmuud.zero.plannerproject.common.AuthUser;
import com.nmuud.zero.plannerproject.schedule.dto.NotificationCreateRequest;
import com.nmuud.zero.plannerproject.schedule.entity.Schedule;
import com.nmuud.zero.plannerproject.schedule.repository.ScheduleRepository;
import com.nmuud.zero.plannerproject.user.entity.User;
import com.nmuud.zero.plannerproject.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final ScheduleRepository scheduleRepository;
    private final UserService userService;

    @Transactional
    public void create(NotificationCreateRequest notificationCreateRequest, AuthUser authUser) {
        final User user = userService.findByUserId(authUser.getId());
        final Schedule notificationSchedule =
                Schedule.notification(
                        notificationCreateRequest.getTitle(),
                        notificationCreateRequest.getNotifyAt(),
                        user
                );

        notificationCreateRequest.getRepeatTimes()
                .forEach(notifyAt ->
                        scheduleRepository.save(Schedule.notification(notificationCreateRequest.getTitle(), notifyAt, user)));

    }
}
