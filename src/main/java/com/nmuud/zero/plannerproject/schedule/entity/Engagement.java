package com.nmuud.zero.plannerproject.schedule.entity;

import com.nmuud.zero.plannerproject.common.enums.RequestStatus;
import com.nmuud.zero.plannerproject.common.util.TimeRange;
import com.nmuud.zero.plannerproject.user.entity.BaseEntity;
import com.nmuud.zero.plannerproject.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Table(name = "engagements")
@Entity
public class Engagement extends BaseEntity {

    @JoinColumn(name = "schedule_id")
    @ManyToOne
    private Schedule schedule;

    @JoinColumn(name = "attendee_id")
    @ManyToOne
    private User attendee;

    @Enumerated(value = EnumType.STRING)
    private RequestStatus requestStatus;


    public Event getEvent() {
        return schedule.toEvent();
    }

    public boolean isOverlapped(TimeRange timeRange) {
        return this.schedule.isOverlapped(timeRange);
    }
}
