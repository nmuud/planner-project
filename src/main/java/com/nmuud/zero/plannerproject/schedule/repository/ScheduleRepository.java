package com.nmuud.zero.plannerproject.schedule.repository;

import com.nmuud.zero.plannerproject.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findAllByWriter_Id(Long userId);
}
