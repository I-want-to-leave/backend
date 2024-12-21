package com.travel.leave.domain.schedule.controller.socket.messageFormat.message.timeline;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.travel.leave.domain.schedule.service.mapper.LocalDateArrayDeserializer;
import java.time.LocalDate;
import java.util.List;

public record ScheduleMessage(
        @JsonDeserialize(using = LocalDateArrayDeserializer.class)
        LocalDate date,
        List<TimeLineMessage> timeLines
) {
}
