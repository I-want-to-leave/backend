package com.travel.leave.domain.schedule.controller.socket.messageFormat.timeline;

import java.time.LocalDate;
import java.util.List;

public record ScheduleMessage(
        LocalDate date,
        List<TimeLineMessage> timeLines
) {
}
