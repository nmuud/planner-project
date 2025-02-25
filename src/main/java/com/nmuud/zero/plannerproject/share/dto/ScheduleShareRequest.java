package com.nmuud.zero.plannerproject.share.dto;

import com.nmuud.zero.plannerproject.share.entity.Share;
import lombok.Data;

@Data
public class ScheduleShareRequest {

    private final Long toUserId;
    private final Share.Direction direction;
}
