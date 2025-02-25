package com.nmuud.zero.plannerproject.schedule.repository;

import com.nmuud.zero.plannerproject.schedule.entity.Engagement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EngagementRepository extends JpaRepository<Engagement, Long> {
    List<Engagement> findAllByAttendee_Id(Long userId);
}
