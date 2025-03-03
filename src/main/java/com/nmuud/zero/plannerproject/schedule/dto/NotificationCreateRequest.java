package com.nmuud.zero.plannerproject.schedule.dto;

import com.nmuud.zero.plannerproject.common.util.TimeUnit;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class NotificationCreateRequest {

    private final String title;
    private final LocalDateTime notifyAt;
    private final RepeatInfo repeatInfo;

    public List<LocalDateTime> getRepeatTimes() {
        if (repeatInfo == null) {
            return Collections.singletonList(notifyAt);
        }
        return IntStream.range(0, repeatInfo.times)
                .mapToObj(i -> {
                            long increment = (long) repeatInfo.interval.intervalValue * i;
                            switch (repeatInfo.interval.timeUnit) {
                                case DAY:
                                    return notifyAt.plusDays(increment);
                                case WEEK:
                                    return notifyAt.plusWeeks(increment);
                                case MONTH:
                                    return notifyAt.plusMonths(increment);
                                case YEAR:
                                    return notifyAt.plusYears(increment);
                                default:
                                    throw new RuntimeException("bad request. not matched time unit");
                            }
                        }
                )
                .collect(Collectors.toList());
    }

    @Data
    public static class RepeatInfo {
        private final Interval interval;
        private final int times;
    }

    @Data
    public static class Interval {
        private final int intervalValue;
        private final TimeUnit timeUnit;
    }
}
